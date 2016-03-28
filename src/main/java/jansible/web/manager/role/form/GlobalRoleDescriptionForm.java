package jansible.web.manager.role.form;

import jansible.model.common.GlobalRoleKey;

public class GlobalRoleDescriptionForm extends GlobalRoleKey{
	private String description;
	
	public GlobalRoleDescriptionForm(){}
	
	public GlobalRoleDescriptionForm(GlobalRoleKey key) {
		super(key.getRoleName());
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
