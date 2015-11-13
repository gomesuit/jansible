package jansible.web.project.top;

import jansible.model.common.ProjectKey;

public class ProjectForm extends ProjectKey{
	private String repositoryUrl;
	
	public ProjectForm(){}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
}