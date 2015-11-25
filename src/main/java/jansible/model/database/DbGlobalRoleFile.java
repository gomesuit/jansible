package jansible.model.database;

import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleKey;

public class DbGlobalRoleFile extends GlobalRoleFileKey{
	
	public DbGlobalRoleFile(){}

	public DbGlobalRoleFile(GlobalRoleKey key){
		super(key);
	}

	public DbGlobalRoleFile(GlobalRoleFileKey key) {
		super(key, key.getFileName());
	}
}
