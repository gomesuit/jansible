package jansible.web.project.form;

import jansible.model.common.ProjectKey;

public class JenkinsInfoForm extends ProjectKey{
	private String jenkinsIpAddress;
	private String jenkinsPort;
	private String jenkinsJobName;
	
	public JenkinsInfoForm(){}
	
	public JenkinsInfoForm(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}

	public String getJenkinsIpAddress() {
		return jenkinsIpAddress;
	}

	public void setJenkinsIpAddress(String jenkinsIpAddress) {
		this.jenkinsIpAddress = jenkinsIpAddress;
	}

	public String getJenkinsPort() {
		return jenkinsPort;
	}

	public void setJenkinsPort(String jenkinsPort) {
		this.jenkinsPort = jenkinsPort;
	}

	public String getJenkinsJobName() {
		return jenkinsJobName;
	}

	public void setJenkinsJobName(String jenkinsJobName) {
		this.jenkinsJobName = jenkinsJobName;
	}
}
