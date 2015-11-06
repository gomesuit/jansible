package jansible.web.project.form;

import jansible.model.common.ServiceGroupVariableKey;

public class ServiceGroupVariableForm extends ServiceGroupVariableKey implements InterfaceVariable{
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
