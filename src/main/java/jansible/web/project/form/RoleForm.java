package jansible.web.project.form;

import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;

public class RoleForm extends RoleKey{
	public RoleForm(){}

	public RoleForm(ProjectKey projectKey){
		super(projectKey);
	}

	public RoleForm(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}
}
