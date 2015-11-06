package jansible.model.database;

import jansible.model.common.ServiceGroupVariableKey;

public class DbServiceGroupVariable extends ServiceGroupVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbServiceGroupVariable(){}

	public DbServiceGroupVariable(String projectName, String environmentName, String groupName, String variableName) {
		super(projectName, environmentName, groupName, variableName);
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
