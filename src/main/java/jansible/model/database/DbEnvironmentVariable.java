package jansible.model.database;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;

public class DbEnvironmentVariable extends EnvironmentVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbEnvironmentVariable(){}

	public DbEnvironmentVariable(EnvironmentKey environmentKey){
		super(environmentKey);
	}

	public DbEnvironmentVariable(EnvironmentVariableKey environmentVariableKey) {
		super(environmentVariableKey, environmentVariableKey.getVariableName());
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
