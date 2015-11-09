package jansible.model.common;

public class TaskDetailKey extends TaskKey{
	private String parameterName;
	
	public TaskDetailKey(){}

	public TaskDetailKey(TaskKey taskKey){
		super(taskKey, taskKey.getTaskId());
	}

	public TaskDetailKey(TaskKey taskKey, String parameterName) {
		this(taskKey);
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
}
