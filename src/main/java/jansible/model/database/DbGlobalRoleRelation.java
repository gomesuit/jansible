package jansible.model.database;

import jansible.model.common.GlobalRoleRelationKey;

public class DbGlobalRoleRelation extends GlobalRoleRelationKey{
	private String tagName;
	
	public DbGlobalRoleRelation(){}

	public DbGlobalRoleRelation(GlobalRoleRelationKey key) {
		super(key, key.getRoleName());
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
