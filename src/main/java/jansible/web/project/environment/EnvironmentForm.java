package jansible.web.project.environment;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;

public class EnvironmentForm extends EnvironmentKey{
	public EnvironmentForm(){}
	
	public EnvironmentForm(ProjectKey projectKey){
		super(projectKey);
	}
	
	public EnvironmentForm(EnvironmentKey environmentKey){
		super(environmentKey, environmentKey.getEnvironmentName());
	}
}
