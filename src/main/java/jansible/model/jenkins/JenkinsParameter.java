package jansible.model.jenkins;

public class JenkinsParameter {
	private String repositoryUrl;
	private String projectName;
	private String groupName;
	
	public String getRepositoryUrl() {
		return repositoryUrl;
	}
	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
