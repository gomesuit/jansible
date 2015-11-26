package jansible.model.common;

public class GlobalRoleTagVariableKey extends GlobalRoleTagKey implements InterfaceVariableKey{
	private String variableName;
	
	public GlobalRoleTagVariableKey(){}

	public GlobalRoleTagVariableKey(GlobalRoleTagKey key){
		super(key, key.getTagName());
	}

	public GlobalRoleTagVariableKey(GlobalRoleTagKey key, String variableName) {
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
