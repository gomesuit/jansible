package jansible.web.manager.role;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleVariableKey;
import jansible.web.project.variable.InterfaceVariable;

public class RoleVariableForm extends GlobalRoleVariableKey implements InterfaceVariable{
	private String value;
	
	public RoleVariableForm(){}

	public RoleVariableForm(GlobalRoleKey key){
		super(key);
	}
	
	public RoleVariableForm(GlobalRoleVariableKey key){
		super(key, key.getVariableName());
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
