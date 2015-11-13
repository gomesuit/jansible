package jansible.web.project.group;

import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.web.project.project.InterfaceVariable;

public class ServiceGroupVariableForm extends ServiceGroupVariableKey implements InterfaceVariable{
	private String value;
	
	public ServiceGroupVariableForm(){}
	
	public ServiceGroupVariableForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey);
	}
	
	public ServiceGroupVariableForm(ServiceGroupVariableKey serviceGroupVariableKey){
		super(serviceGroupVariableKey, serviceGroupVariableKey.getVariableName());
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
