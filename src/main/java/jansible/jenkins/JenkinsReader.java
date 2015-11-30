package jansible.jenkins;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class JenkinsReader {

	private String executeGet(String url) {
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

}
