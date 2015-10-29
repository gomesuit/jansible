package jansible.model.database;

public class DbProject {
	private String projectName;
	
	public DbProject(){}
	
	public DbProject(String projectName){
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
