package jansible.model.database;

import jansible.model.common.TaskDetailKey;

public class DbTaskDetail extends TaskDetailKey{
	private String parameterValue;
	
	public DbTaskDetail(){}

	public DbTaskDetail(String projectName, String roleName, Integer taskId, String parameterName) {
		super(projectName, roleName, taskId, parameterName);
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
