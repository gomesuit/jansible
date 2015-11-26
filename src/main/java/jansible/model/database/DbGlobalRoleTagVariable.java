package jansible.model.database;

import jansible.model.common.GlobalRoleTagVariableKey;

public class DbGlobalRoleTagVariable extends GlobalRoleTagVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbGlobalRoleTagVariable(){}

	public DbGlobalRoleTagVariable(GlobalRoleTagVariableKey key) {
		super(key, key.getVariableName());
	}

	public DbGlobalRoleTagVariable(DbGlobalRoleVariable dbGlobalRoleVariable, String tagName) {
		this.setRoleName(dbGlobalRoleVariable.getRoleName());
		this.setTagName(tagName);
		this.setVariableName(dbGlobalRoleVariable.getVariableName());
		this.setValue(dbGlobalRoleVariable.getValue());
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
