package jansible.model.database;

import jansible.model.common.ServerParameterKey;


public class DbServerParameter extends ServerParameterKey{
	private String parameterValue;
	
	public DbServerParameter(){}

	public DbServerParameter(ServerParameterKey serverParameterKey) {
		super(serverParameterKey, serverParameterKey.getParameterName());
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
