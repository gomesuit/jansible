package jansible.web.manager.top;

import jansible.model.common.GlobalRoleKey;

public class GlobalRoleForm extends GlobalRoleKey{
	private String repositoryUrl;
	
	public GlobalRoleForm(){}

	public GlobalRoleForm(GlobalRoleKey globalRoleKey){
		super(globalRoleKey.getRoleName());
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
}
