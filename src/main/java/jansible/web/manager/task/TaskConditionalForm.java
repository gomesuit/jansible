package jansible.web.manager.task;

import jansible.model.common.TaskConditionalKey;
import jansible.model.common.TaskKey;

public class TaskConditionalForm extends TaskConditionalKey{
	private String conditionalValue;
	
	public TaskConditionalForm(){}

	public TaskConditionalForm(TaskKey taskKey){
		super(taskKey);
	}

	public TaskConditionalForm(TaskConditionalKey taskConditionalKey){
		super(taskConditionalKey, taskConditionalKey.getConditionalName());
	}

	public String getConditionalValue() {
		return conditionalValue;
	}

	public void setConditionalValue(String conditionalValue) {
		this.conditionalValue = conditionalValue;
	}
}
