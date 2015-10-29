package jansible.model.database;

import jansible.model.common.ServerKey;

public class DbServer extends ServerKey{
	public DbServer(){}

	public DbServer(String projectName, String environmentName,
			String groupName, String serverName) {
		super(projectName, environmentName, groupName, serverName);
	}
}
