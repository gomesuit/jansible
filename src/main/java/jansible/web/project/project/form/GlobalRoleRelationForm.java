package jansible.web.project.project.form;

import jansible.model.common.GlobalRoleRelationKey;
import jansible.model.common.ProjectKey;

public class GlobalRoleRelationForm extends GlobalRoleRelationKey{
	private String tagName;
	
	public GlobalRoleRelationForm(){}

	public GlobalRoleRelationForm(ProjectKey key){
		super(key);
	}

	public GlobalRoleRelationForm(GlobalRoleRelationKey key){
		super(key, key.getRoleName());
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
