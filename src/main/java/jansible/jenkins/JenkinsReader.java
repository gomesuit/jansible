package jansible.jenkins;

import jansible.jenkins.test.Action;
import jansible.jenkins.test.Build;
import jansible.jenkins.test.Result;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JenkinsReader {

	private static String executeGet(String url) {
		StringBuffer stringBuffer = new StringBuffer();

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet getMethod = new HttpGet(url);

			try (CloseableHttpResponse response = httpClient.execute(getMethod)) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					stringBuffer.append(EntityUtils.toString(entity, StandardCharsets.UTF_8));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stringBuffer.toString();
	}
	
	private String getConsoleTextUrl(String ipAddress, String port, String jobName, int buildNumber){
		return "http://" + ipAddress + ":" + port + "/job/" + jobName + "/" + buildNumber + "/consoleText";
	}
	
	public String getBuildResult(JenkinsReadInfo info){
		String url = getConsoleTextUrl(info.getIpAddress(), info.getPort(), info.getJobName(), info.getBuildNumber());
		return executeGet(url);
	}
	
	public String getBuildResult(JenkinsInfo info, int applyHistroyId){
		int buildNumber = getBuildNumber(info, applyHistroyId);
		JenkinsReadInfo jenkinsReadInfo = new JenkinsReadInfo();
		jenkinsReadInfo.setIpAddress(info.getIpAddress());
		jenkinsReadInfo.setPort(info.getPort());
		jenkinsReadInfo.setJobName(info.getJobName());
		jenkinsReadInfo.setBuildNumber(buildNumber);
		
		return getBuildResult(jenkinsReadInfo);
	}
	
	public int getBuildNumber(JenkinsInfo info, int applyHistroyId){
		Result result;
		try {
			result = getBuildResultObject(info);
		} catch (Exception e) {
			return 0;
		}
		return getBuildNumber(result, applyHistroyId);
	}
	
	private Result getBuildResultObject(JenkinsInfo info) throws Exception{
		String url = getBuildResultUrl(info);
		String json = executeGet(url);
		ObjectMapper mapper = new ObjectMapper();
		Result result = mapper.readValue(json, Result.class);
		return result;
	}
	
	private int getBuildNumber(Result result, int applyHistroyId){
		for(Build build : result.getBuilds()){
			Action action = build.getActions().get(0);
			JenkinsParameter jenkinsParameter = getJenkinsParameter(action.getParameters());
			if(applyHistroyId == jenkinsParameter.getApplyHistroyId()){
				return build.getNumber();
			}
		}
		return 0;
	}
	
	private JenkinsParameter getJenkinsParameter(List<JenkinsParameterMap> parameters) {
		Map<String, String> map = getMap(parameters);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(map.get("projectName"));
		jenkinsParameter.setGroupName(map.get("groupName"));
		jenkinsParameter.setRepositoryUrl(map.get("repositoryUrl"));
		jenkinsParameter.setTagName(map.get("tagName"));
		jenkinsParameter.setApplyHistroyId(Integer.parseInt(map.get("applyHistroyId")));
		return jenkinsParameter;
	}
	
	private Map<String, String> getMap(List<JenkinsParameterMap> parameters){
		Map<String, String> map = new HashMap<>();
		for(JenkinsParameterMap jenkinsParameterMap : parameters){
			map.put(jenkinsParameterMap.getName(), jenkinsParameterMap.getValue());
		}
		return map;
	}

	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException{
		String url = "http://192.168.33.11:8080/job/ansible/api/json?depth=1";
		String json = executeGet(url);
		ObjectMapper mapper = new ObjectMapper();
		Result hoge = mapper.readValue(json, Result.class);
		System.out.println(hoge);
	}
	
	private String getBuildResultUrl(JenkinsInfo info){
		String ipAddress = info.getIpAddress();
		String port = info.getPort();
		String jobName = info.getJobName();
		return "http://" + ipAddress + ":" + port + "/job/" + jobName + "/api/json?depth=1";
	}
}
