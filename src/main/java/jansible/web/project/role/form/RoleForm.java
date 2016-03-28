package jansible.web.project.role.form;

import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;

public class RoleForm extends RoleKey{
	private String description;
	
	public RoleForm(){}

	public RoleForm(ProjectKey projectKey){
		super(projectKey);
	}

	public RoleForm(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
