package jansible.model.common;

public class ServerRelationKey extends ServiceGroupKey{
	private String serverName;
	
	public ServerRelationKey(){}
	
	public ServerRelationKey(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}
	
	public ServerRelationKey(ServiceGroupKey serviceGroupKey, String serverName){
		this(serviceGroupKey);
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((serverName == null) ? 0 : serverName.hashCode());
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
		ServerRelationKey other = (ServerRelationKey) obj;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		return true;
	}
}
