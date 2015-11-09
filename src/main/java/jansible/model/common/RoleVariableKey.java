package jansible.model.common;

public class RoleVariableKey extends RoleKey implements InterfaceVariableKey{
	private String variableName;
	
	public RoleVariableKey(){}

	public RoleVariableKey(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}

	public RoleVariableKey(RoleKey roleKey, String variableName) {
		this(roleKey);
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
