package jansible.web.project.project;

import jansible.model.common.ProjectKey;

public class GitForm extends ProjectKey{
	private String userName;
	private String password;
	private String comment;
	
	public GitForm(){}

	public GitForm(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
