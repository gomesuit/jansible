package jansible.web.project.project.form;

import jansible.model.common.ProjectKey;

public class GitConpareForm extends ProjectKey{
	private String baseTag;
	private String conpareTag;
	
	public GitConpareForm(){}

	public GitConpareForm(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}

	public String getBaseTag() {
		return baseTag;
	}

	public void setBaseTag(String baseTag) {
		this.baseTag = baseTag;
	}

	public String getConpareTag() {
		return conpareTag;
	}

	public void setConpareTag(String conpareTag) {
		this.conpareTag = conpareTag;
	}
}
