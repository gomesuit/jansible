package jansible.model.database;

import jansible.model.common.ServerKey;

public class DbServer extends ServerKey{
	
	public DbServer(){}
	
	public DbServer(DbServiceGroup dbServiceGroup, String serverName){
		super(dbServiceGroup.getProjectName(), dbServiceGroup.getEnvironmentName(), dbServiceGroup.getGroupName(), serverName);
	}
}
