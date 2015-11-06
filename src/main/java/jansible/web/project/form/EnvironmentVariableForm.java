package jansible.web.project.form;

import jansible.model.common.EnvironmentVariableKey;

public class EnvironmentVariableForm extends EnvironmentVariableKey implements InterfaceVariable{
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
