package jansible.web.project.group.form;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServiceGroupKey;

public class ServiceGroupForm extends ServiceGroupKey{
	public ServiceGroupForm(){}

	public ServiceGroupForm(ProjectKey projectKey){
		this.setProjectName(projectKey.getProjectName());
	}

	public ServiceGroupForm(EnvironmentKey environmentKey){
		super(environmentKey);
	}
}
