package jansible.model.common;

public class ServerKey extends ServiceGroupKey{
	private String serverName;
	
	public ServerKey(){}

	public ServerKey(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}
	
	public ServerKey(ServiceGroupKey serviceGroupKey, String serverName){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
