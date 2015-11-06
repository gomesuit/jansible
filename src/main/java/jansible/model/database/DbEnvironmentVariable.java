package jansible.model.database;

import jansible.model.common.EnvironmentVariableKey;

public class DbEnvironmentVariable extends EnvironmentVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbEnvironmentVariable(){}

	public DbEnvironmentVariable(String projectName, String environmentName, String variableName) {
		super(projectName, environmentName, variableName);
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
