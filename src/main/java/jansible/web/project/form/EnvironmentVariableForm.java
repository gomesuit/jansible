package jansible.web.project.form;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;

public class EnvironmentVariableForm extends EnvironmentVariableKey implements InterfaceVariable{
	private String value;
	
	public EnvironmentVariableForm(){};
	
	public EnvironmentVariableForm(EnvironmentKey environmentKey){
		super(environmentKey);
	}
	
	public EnvironmentVariableForm(EnvironmentVariableKey environmentVariableKey){
		super(environmentVariableKey, environmentVariableKey.getVariableName());
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
