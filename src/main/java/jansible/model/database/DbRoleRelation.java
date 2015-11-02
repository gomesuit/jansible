package jansible.model.database;

import jansible.model.common.RoleRelationKey;

public class DbRoleRelation extends RoleRelationKey{
	private int sort;
	
	public DbRoleRelation(){}
	
	public DbRoleRelation(String projectName, String environmentName, String groupName, String roleName) {
		super(projectName, environmentName, groupName, roleName);
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
