package jansible.model.database;

import jansible.model.common.GlobalRoleKey;

public class DbGlobalRole extends GlobalRoleKey{
	public DbGlobalRole(){}

	public DbGlobalRole(GlobalRoleKey key) {
		super(key.getRoleName());
	}
}
