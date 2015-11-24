package jansible.model.database;

import jansible.model.common.TaskDetailKey;
import jansible.model.common.TaskKey;

public class DbTaskDetail extends TaskDetailKey{
	private String parameterValue;
	
	public DbTaskDetail(){}

	public DbTaskDetail(TaskKey taskKey) {
		super(taskKey);
	}

	public DbTaskDetail(TaskDetailKey taskDetailKey) {
		super(taskDetailKey, taskDetailKey.getParameterName());
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
