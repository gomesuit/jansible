package jansible.model.common;

public class ServerKey extends ServiceGroupKey{
	private String serverName;
	
	public ServerKey(){}

	public ServerKey(String projectName, String environmentName, String groupName, String serverName) {
		super(projectName, environmentName, groupName);
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
