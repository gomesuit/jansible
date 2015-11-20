package jansible.model.database;

import jansible.model.common.ServerKey;

public class DbServer extends ServerKey{
	private String environmentName;
	
	public DbServer(){}

	public DbServer(ServerKey serverKey) {
		super(serverKey, serverKey.getServerName());
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
}
