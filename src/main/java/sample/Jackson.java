package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jansible.jenkins.JenkinsParameter;

public class Jackson {

	public static void main(String[] args) throws JsonProcessingException {
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName("projectName");
		jenkinsParameter.setRepositoryUrl("repositoryUrl");
		jenkinsParameter.setGroupName("groupName");
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(jenkinsParameter);
		System.out.println(json);
	}

}
