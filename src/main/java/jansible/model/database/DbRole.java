package jansible.model.database;

import jansible.model.common.RoleKey;

public class DbRole extends RoleKey{
	private String description;
	
	public DbRole(){}

	public DbRole(RoleKey roleKey) {
		super(roleKey, roleKey.getRoleName());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
