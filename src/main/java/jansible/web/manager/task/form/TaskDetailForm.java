package jansible.web.manager.task.form;

import java.util.List;

import jansible.model.common.GlobalTaskKey;

public class TaskDetailForm extends GlobalTaskKey{
	private String description;
	private List<TaskParameter> taskParameterList;
	
	public TaskDetailForm(){}
	
	public TaskDetailForm(GlobalTaskKey key){
		super(key, key.getTaskId());
	}

	public List<TaskParameter> getTaskParameterList() {
		return taskParameterList;
	}
	public void setTaskParameterList(List<TaskParameter> taskParameterList) {
		this.taskParameterList = taskParameterList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
