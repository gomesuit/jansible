package jansible.model.database;

public class DbServer {
	private String projectName;
	private String groupName;
	private String serverName;
	
	public DbServer(){}
	
	public DbServer(DbServiceGroup dbServiceGroup, String serverName){
		this(dbServiceGroup.getProjectName(), dbServiceGroup.getGroupName(), serverName);
	}
	
	public DbServer(String projectName, String groupName, String serverName) {
		super();
		this.projectName = projectName;
		this.groupName = groupName;
		this.serverName = serverName;
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
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
