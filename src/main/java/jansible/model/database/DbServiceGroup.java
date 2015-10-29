package jansible.model.database;

public class DbServiceGroup {
	private String projectName;
	private String groupName;
	
	public DbServiceGroup(){}
	
	public DbServiceGroup(DbProject dbProject, String groupName){
		this(dbProject.getProjectName(), groupName);
	}
	
	public DbServiceGroup(String projectName, String groupName) {
		super();
		this.projectName = projectName;
		this.groupName = groupName;
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
