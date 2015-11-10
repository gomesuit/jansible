package jansible.web.project.form;

import jansible.model.common.ServiceGroupKey;

public class BuildForm extends ServiceGroupKey{	
	public BuildForm(){}
	
	public BuildForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}
}
