package jansible.web.manager.task.form;

import jansible.model.common.GlobalTaskConditionalKey;
import jansible.model.common.GlobalTaskKey;

public class TaskConditionalForm extends GlobalTaskConditionalKey{
	private String conditionalValue;
	
	public TaskConditionalForm(){}

	public TaskConditionalForm(GlobalTaskKey key){
		super(key);
	}

	public TaskConditionalForm(GlobalTaskConditionalKey key){
		super(key, key.getConditionalName());
	}

	public String getConditionalValue() {
		return conditionalValue;
	}

	public void setConditionalValue(String conditionalValue) {
		this.conditionalValue = conditionalValue;
	}
}
