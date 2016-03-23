package jansible.web.project.project.form;

import jansible.model.common.GlobalRoleRelationKey;
import jansible.model.common.ProjectKey;

public class GlobalRoleRelationTagUpdateForm extends GlobalRoleRelationKey{
	private String tagName;
	
	public GlobalRoleRelationTagUpdateForm(){}

	public GlobalRoleRelationTagUpdateForm(ProjectKey key){
		super(key);
	}

	public GlobalRoleRelationTagUpdateForm(GlobalRoleRelationKey key){
		super(key, key.getRoleName());
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
