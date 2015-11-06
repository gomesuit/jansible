package jansible.model.common;

public class EnvironmentVariableKey extends EnvironmentKey implements InterfaceVariableKey{
	private String variableName;
	
	public EnvironmentVariableKey(){}

	public EnvironmentVariableKey(String projectName, String environmentName, String variableName) {
		super(projectName, environmentName);
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
