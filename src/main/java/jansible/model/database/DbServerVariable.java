package jansible.model.database;

import jansible.model.common.ServerVariableKey;


public class DbServerVariable extends ServerVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbServerVariable(){}

	public DbServerVariable(ServerVariableKey serverVariableKey) {
		super(serverVariableKey, serverVariableKey.getVariableName());
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
