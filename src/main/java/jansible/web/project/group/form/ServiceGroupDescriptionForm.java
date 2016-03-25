package jansible.web.project.group.form;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServiceGroupKey;

public class ServiceGroupDescriptionForm extends ServiceGroupKey{
	private String description;
	
	public ServiceGroupDescriptionForm(){}

	public ServiceGroupDescriptionForm(ProjectKey projectKey){
		this.setProjectName(projectKey.getProjectName());
	}

	public ServiceGroupDescriptionForm(EnvironmentKey environmentKey){
		super(environmentKey);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
