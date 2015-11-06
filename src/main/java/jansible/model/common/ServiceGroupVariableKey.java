package jansible.model.common;

public class ServiceGroupVariableKey extends ServiceGroupKey implements InterfaceVariableKey{
	private String variableName;
	
	public ServiceGroupVariableKey(){}

	public ServiceGroupVariableKey(String projectName, String environmentName, String groupName, String variableName) {
		super(projectName, environmentName, groupName);
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
