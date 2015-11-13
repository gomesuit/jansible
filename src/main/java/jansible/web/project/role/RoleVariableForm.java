package jansible.web.project.role;

import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.web.project.project.InterfaceVariable;

public class RoleVariableForm extends RoleVariableKey implements InterfaceVariable{
	private String value;
	
	public RoleVariableForm(){}

	public RoleVariableForm(RoleKey roleKey){
		super(roleKey);
	}
	
	public RoleVariableForm(RoleVariableKey roleVariableKey){
		super(roleVariableKey, roleVariableKey.getVariableName());
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
