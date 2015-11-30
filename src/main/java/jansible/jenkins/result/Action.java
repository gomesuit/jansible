package jansible.jenkins.result;

import jansible.jenkins.JenkinsParameterMap;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Action {
	private List<JenkinsParameterMap> parameters;

	public List<JenkinsParameterMap> getParameters() {
		return parameters;
	}

	public void setParameters(List<JenkinsParameterMap> parameters) {
		this.parameters = parameters;
	}
}
