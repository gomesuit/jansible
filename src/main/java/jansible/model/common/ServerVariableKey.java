package jansible.model.common;

public class ServerVariableKey extends ServerKey implements InterfaceVariableKey{
	private String variableName;
	
	public ServerVariableKey(){}

	public ServerVariableKey(ServerKey serverKey){
		super(serverKey, serverKey.getServerName());
	}

	public ServerVariableKey(ServerKey serverKey, String variableName) {
		this(serverKey);
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
