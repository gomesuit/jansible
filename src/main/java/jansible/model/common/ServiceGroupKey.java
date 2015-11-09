package jansible.model.common;

public class ServiceGroupKey extends EnvironmentKey{
	private String groupName;
	
	public ServiceGroupKey(){}

	public ServiceGroupKey(EnvironmentKey environmentKey){
		super(environmentKey, environmentKey.getEnvironmentName());
	}
	
	public ServiceGroupKey(EnvironmentKey environmentKey, String groupName){
		this(environmentKey);
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
