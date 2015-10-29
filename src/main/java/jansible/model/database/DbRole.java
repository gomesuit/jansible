package jansible.model.database;

import jansible.model.common.RoleKey;

public class DbRole extends RoleKey{
	public DbRole(){}

	public DbRole(String projectName, String roleName) {
		super(projectName, roleName);
	}
}
