package jansible.model.common;

public class Group{
	private String environmentName;
	private String groupName;
	
	public Group(String environmentName, String groupName){
		this.environmentName = environmentName;
		this.groupName = groupName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
}
