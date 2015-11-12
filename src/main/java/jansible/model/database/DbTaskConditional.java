package jansible.model.database;

import jansible.model.common.TaskConditionalKey;

public class DbTaskConditional extends TaskConditionalKey{
	private String conditionalValue;
	
	public DbTaskConditional(){}

	public DbTaskConditional(TaskConditionalKey taskConditionalKey) {
		super(taskConditionalKey, taskConditionalKey.getConditionalName());
	}

	public String getConditionalValue() {
		return conditionalValue;
	}

	public void setConditionalValue(String conditionalValue) {
		this.conditionalValue = conditionalValue;
	}
}
