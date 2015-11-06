package jansible.model.database;

import jansible.model.common.ServerVariableKey;


public class DbServerVariable extends ServerVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbServerVariable(){}

	public DbServerVariable(String projectName, String environmentName, String groupName, String serverName, String variableName) {
		super(projectName, environmentName, groupName, serverName, variableName);
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
