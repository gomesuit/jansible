package jansible.web.project.form;

import jansible.model.common.ServerVariableKey;

public class ServerVariableForm extends ServerVariableKey implements InterfaceVariable{
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
