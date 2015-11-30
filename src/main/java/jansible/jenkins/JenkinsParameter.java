package jansible.jenkins;

public class JenkinsParameter {
	private String repositoryUrl;
	private String projectName;
	private String groupName;
	private String tagName;
	private int applyHistroyId;
	
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
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public int getApplyHistroyId() {
		return applyHistroyId;
	}
	public void setApplyHistroyId(int applyHistroyId) {
		this.applyHistroyId = applyHistroyId;
	}
}
