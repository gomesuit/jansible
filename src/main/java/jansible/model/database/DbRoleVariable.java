package jansible.model.database;

import jansible.model.common.RoleVariableKey;

public class DbRoleVariable extends RoleVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbRoleVariable(){}

	public DbRoleVariable(String projectName, String roleName, String variableName) {
		super(projectName, roleName, variableName);
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
