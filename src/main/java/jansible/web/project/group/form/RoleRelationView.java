package jansible.web.project.group.form;

import jansible.model.common.RoleRelationKey;

public class RoleRelationView extends RoleRelationKey{
	private RoleType roleType;
	private String gitHubUrl;

	public RoleRelationView(){}
	
	public RoleRelationView(RoleRelationKey roleRelationKey) {
		super(roleRelationKey, roleRelationKey.getRoleName());
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getGitHubUrl() {
		return gitHubUrl;
	}

	public void setGitHubUrl(String gitHubUrl) {
		this.gitHubUrl = gitHubUrl;
	}
}
