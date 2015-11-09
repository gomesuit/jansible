package jansible.model.database;

import jansible.model.common.ProjectKey;

public class DbProject extends ProjectKey{
	private String repositoryUrl;
	
	public DbProject(){}

	public DbProject(String projectName, String repositoryUrl) {
		super(projectName);
		this.repositoryUrl = repositoryUrl;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
}
