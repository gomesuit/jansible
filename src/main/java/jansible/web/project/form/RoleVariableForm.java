package jansible.web.project.form;

import jansible.model.common.RoleVariableKey;

public class RoleVariableForm extends RoleVariableKey implements InterfaceVariable{
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
