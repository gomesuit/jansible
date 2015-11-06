package jansible.model.common;

public class VariableKey extends ProjectKey{
	private Target target;
	private String targetName;
	private String variableName;

	public VariableKey(){}
	
	public VariableKey(String projectName, Target target, String targetName, String variableName) {
		super(projectName);
		this.target = target;
		this.targetName = targetName;
		this.variableName = variableName;
	}
	
	public Target getTarget() {
		return target;
	}
	public void setTarget(Target target) {
		this.target = target;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
