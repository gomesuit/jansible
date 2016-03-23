package jansible.web.project.environment.form;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;
import jansible.web.project.variable.InterfaceVariable;

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
