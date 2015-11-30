package jansible.jenkins;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JenkinsBuilder {
	
	public void build(JenkinsInfo jenkinsInfo, JenkinsParameter jenkinsParameter){
		build(getJenkinsJobUrl(jenkinsInfo), jenkinsParameter);
	}
	
	private String getJenkinsJobUrl(JenkinsInfo jenkinsInfo){
		String ipAddress = jenkinsInfo.getIpAddress();
		String port = jenkinsInfo.getPort();
		String jobName = jenkinsInfo.getJobName();
		
		return "http://" + ipAddress + ":" + port + "/job/" + jobName + "/build?delay=0sec";
	}
	
	private void build(String buildUrl, JenkinsParameter jenkinsParameter){		
		String json = getJenkinsJson(jenkinsParameter);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json", json));
		
		executePost(buildUrl, params);
	}
	
	private String getJenkinsJson(JenkinsParameter jenkinsParameter){
		JenkinsParameters jenkinsParameters = new JenkinsParameters();
		jenkinsParameters.addParameter("projectName", jenkinsParameter.getProjectName());
		jenkinsParameters.addParameter("repositoryUrl", jenkinsParameter.getRepositoryUrl());
		jenkinsParameters.addParameter("groupName", jenkinsParameter.getGroupName());
		jenkinsParameters.addParameter("tagName", jenkinsParameter.getTagName());
		jenkinsParameters.addParameter("applyHistroyId", Integer.toString(jenkinsParameter.getApplyHistroyId()));
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(jenkinsParameters);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

    private void executePost(String url, List<NameValuePair> params) {
        System.out.println("===== HTTP POST Start =====");
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postMethod = new HttpPost(url);
            
    		postMethod.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(postMethod)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    System.out.println(EntityUtils.toString(entity, StandardCharsets.UTF_8));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===== HTTP POST End =====");
    }
}
