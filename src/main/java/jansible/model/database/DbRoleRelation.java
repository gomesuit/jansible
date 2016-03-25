package jansible.model.database;

import jansible.model.common.RoleRelationKey;
import jansible.web.project.group.form.RoleType;

public class DbRoleRelation extends RoleRelationKey implements InterfaceDbSort{
	private int sort;
	private RoleType roleType;
	
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

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
