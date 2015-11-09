package jansible.model.database;

import jansible.model.common.ServiceGroupVariableKey;

public class DbServiceGroupVariable extends ServiceGroupVariableKey implements InterfaceDbVariable{
	private String value;
	
	public DbServiceGroupVariable(){}

	public DbServiceGroupVariable(ServiceGroupVariableKey serviceGroupVariableKey) {
		super(serviceGroupVariableKey, serviceGroupVariableKey.getVariableName());
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
