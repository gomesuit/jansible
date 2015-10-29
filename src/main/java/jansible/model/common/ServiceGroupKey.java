package jansible.model.common;

public class ServiceGroupKey extends EnvironmentKey{
	private String groupName;
	
	public ServiceGroupKey(){}

	public ServiceGroupKey(String projectName, String environmentName, String groupName) {
		super(projectName, environmentName);
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
