package jansible.model.common;

public class ProjectKey {
	private String projectName;
	
	public ProjectKey(){}

	public ProjectKey(String projectName) {
		super();
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
