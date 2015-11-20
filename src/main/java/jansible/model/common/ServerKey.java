package jansible.model.common;

public class ServerKey extends ProjectKey{
	private String serverName;
	
	public ServerKey(){}

	public ServerKey(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}
	
	public ServerKey(ProjectKey projectKey, String serverName){
		this(projectKey);
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
