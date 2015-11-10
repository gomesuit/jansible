package sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Http {

	public static void main(String[] args) {
        executeGet();
        //executePost();
	}

    private static void executeGet() {
        System.out.println("===== HTTP GET Start =====");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        // もしくは
        // try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            //HttpGet getMethod = new HttpGet("http://localhost:8080/get?param=value");
            HttpGet getMethod = new HttpGet("http://192.168.33.11:8080/job/test2/buildWithParameters?no=1");
            
            try (CloseableHttpResponse response = httpClient.execute(getMethod)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    System.out.println(EntityUtils.toString(entity, StandardCharsets.UTF_8));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===== HTTP GET End =====");
    }

    private static void executePost() {
        System.out.println("===== HTTP POST Start =====");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        // もしくは
        // try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost postMethod = new HttpPost("http://localhost:8080/post");
            
            StringBuilder builder = new StringBuilder();
            builder.append("POST Body");
            builder.append("\r\n");
            builder.append("Hello Http Server!!");
            builder.append("\r\n");
            
            postMethod.setEntity(new StringEntity(builder.toString(), StandardCharsets.UTF_8));

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
