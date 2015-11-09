package jansible.model.database;

import jansible.model.common.TaskDetailKey;

public class DbTaskDetail extends TaskDetailKey{
	private String parameterValue;
	
	public DbTaskDetail(){}

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
