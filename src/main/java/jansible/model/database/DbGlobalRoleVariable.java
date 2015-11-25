package jansible.model.database;

import jansible.model.common.GlobalRoleVariableKey;

public class DbGlobalRoleVariable extends GlobalRoleVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbGlobalRoleVariable(){}

	public DbGlobalRoleVariable(GlobalRoleVariableKey key) {
		super(key, key.getVariableName());
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
