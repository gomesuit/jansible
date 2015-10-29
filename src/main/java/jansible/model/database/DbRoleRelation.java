package jansible.model.database;

import jansible.model.common.ServiceGroupKey;

public class DbRoleRelation extends ServiceGroupKey{
	private String roleName;
	
	public DbRoleRelation(){}
	
	public DbRoleRelation(String roleName) {
		super();
		this.roleName = roleName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
