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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceGroupKey other = (ServiceGroupKey) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}
}
