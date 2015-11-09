package jansible.model.common;

public class EnvironmentVariableKey extends EnvironmentKey implements InterfaceVariableKey{
	private String variableName;
	
	public EnvironmentVariableKey(){}

	public EnvironmentVariableKey(EnvironmentKey environmentKey){
		super(environmentKey, environmentKey.getEnvironmentName());
	}

	public EnvironmentVariableKey(EnvironmentKey environmentKey, String variableName) {
		this(environmentKey);
		this.variableName = variableName;
	}

	@Override
	public String getVariableName() {
		return variableName;
	}

	@Override
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
