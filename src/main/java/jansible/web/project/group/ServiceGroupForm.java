package jansible.web.project.group;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ServiceGroupKey;

public class ServiceGroupForm extends ServiceGroupKey{
	public ServiceGroupForm(){}

	public ServiceGroupForm(EnvironmentKey environmentKey){
		super(environmentKey);
	}
}
