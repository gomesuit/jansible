package sample;

import jansible.jenkins.JenkinsInfo;
import jansible.jenkins.result.JenkinsReader;

public class Main2 {

	public static void main(String[] args) {
		JenkinsReader jenkinsReader = new JenkinsReader();
		JenkinsInfo info = new JenkinsInfo();
		info.setIpAddress("192.168.33.11");
		info.setJobName("ansible");
		info.setPort("8080");
		
		System.out.println(jenkinsReader.getBuildResult(info, 30));
	}
}
