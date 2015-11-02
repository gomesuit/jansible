package jansible.model.common;

public class TaskDetailKey extends TaskKey{
	private String parameterName;
	
	public TaskDetailKey(){}

	public TaskDetailKey(String projectName, String roleName, Integer taskId, String parameterName) {
		super(projectName, roleName, taskId);
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
}
