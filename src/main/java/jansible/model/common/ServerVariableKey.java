package jansible.model.common;

public class ServerVariableKey extends ServerKey implements InterfaceVariableKey{
	private String variableName;
	
	public ServerVariableKey(){}

	public ServerVariableKey(String projectName, String environmentName, String groupName, String serverName, String variableName) {
		super(projectName, environmentName, groupName, serverName);
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
