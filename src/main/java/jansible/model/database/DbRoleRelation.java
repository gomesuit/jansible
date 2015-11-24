package jansible.model.database;

import jansible.model.common.RoleRelationKey;

public class DbRoleRelation extends RoleRelationKey implements InterfaceDbSort{
	private int sort;
	
	public DbRoleRelation(){}
	
	public DbRoleRelation(RoleRelationKey roleRelationKey) {
		super(roleRelationKey, roleRelationKey.getRoleName());
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
