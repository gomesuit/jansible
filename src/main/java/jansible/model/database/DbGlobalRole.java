package jansible.model.database;

import jansible.model.common.GlobalRoleKey;

public class DbGlobalRole extends GlobalRoleKey{
	private String repositoryUrl;
	
	public DbGlobalRole(){}

	public DbGlobalRole(GlobalRoleKey key) {
		super(key.getRoleName());
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
}
