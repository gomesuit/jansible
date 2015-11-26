package jansible.web.project.top;

import jansible.git.GitCredentialInfo;
import jansible.model.common.ProjectKey;

public class ProjectForm extends ProjectKey implements GitCredentialInfo{
	private String repositoryUrl;
	private String userName;
	private String password;
	
	public ProjectForm(){}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
}
