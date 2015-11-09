package jansible.model.database;

import jansible.model.common.RoleVariableKey;

public class DbRoleVariable extends RoleVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbRoleVariable(){}

	public DbRoleVariable(RoleVariableKey roleVariableKey) {
		super(roleVariableKey, roleVariableKey.getVariableName());
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
