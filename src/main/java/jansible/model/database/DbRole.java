package jansible.model.database;

public class DbRole {
	private String projectName;
	private String roleName;
	
	public DbRole(){}
	
	public DbRole(DbProject dbProject, String roleName){
		this(dbProject.getProjectName(), roleName);
	}
	
	public DbRole(String projectName, String roleName) {
		super();
		this.projectName = projectName;
		this.roleName = roleName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
