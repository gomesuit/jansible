package jansible.jenkins;

import java.util.ArrayList;
import java.util.List;

public class JenkinsParameters {
	private List<JenkinsParameterMap> parameter = new ArrayList<>();
	
	public void addParameter(String name, String value){
		JenkinsParameterMap jenkinsParameterMap = new JenkinsParameterMap();
		jenkinsParameterMap.setName(name);
		jenkinsParameterMap.setValue(value);
		parameter.add(jenkinsParameterMap);
	}

	public List<JenkinsParameterMap> getParameter() {
		return parameter;
	}

	public void setParameter(List<JenkinsParameterMap> parameter) {
		this.parameter = parameter;
	}
}
