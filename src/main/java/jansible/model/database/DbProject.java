package jansible.model.database;

import jansible.model.common.ProjectKey;

public class DbProject extends ProjectKey{
	private String repositoryUrl;
	private String jenkinsIpAddress;
	private String jenkinsPort;
	private String jenkinsJobName;
	
	public DbProject(){}

	public DbProject(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}

	public DbProject(ProjectKey projectKey, String repositoryUrl) {
		this(projectKey);
		this.repositoryUrl = repositoryUrl;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
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
