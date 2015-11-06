package jansible.model.common;

public class RoleVariableKey extends RoleKey implements InterfaceVariableKey{
	private String variableName;
	
	public RoleVariableKey(){}

	public RoleVariableKey(String projectName, String roleName, String variableName) {
		super(projectName, roleName);
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
