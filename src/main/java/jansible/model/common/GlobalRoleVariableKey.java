package jansible.model.common;

public class GlobalRoleVariableKey extends GlobalRoleKey implements InterfaceVariableKey{
	private String variableName;
	
	public GlobalRoleVariableKey(){}

	public GlobalRoleVariableKey(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public GlobalRoleVariableKey(GlobalRoleKey key, String variableName) {
		this(key);
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
