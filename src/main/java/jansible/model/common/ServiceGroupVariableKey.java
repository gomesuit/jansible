package jansible.model.common;

public class ServiceGroupVariableKey extends ServiceGroupKey implements InterfaceVariableKey{
	private String variableName;
	
	public ServiceGroupVariableKey(){}

	public ServiceGroupVariableKey(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}

	public ServiceGroupVariableKey(ServiceGroupKey serviceGroupKey, String variableName) {
		this(serviceGroupKey);
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
