package jansible.web.project.role.form;

import java.util.List;

import jansible.model.database.DbGlobalRoleRelation;

public class GlobalRoleRelationView extends DbGlobalRoleRelation{
	private List<String> tagList;

	public GlobalRoleRelationView(DbGlobalRoleRelation db) {
		super(db);
		this.setTagName(db.getTagName());
	}

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
}