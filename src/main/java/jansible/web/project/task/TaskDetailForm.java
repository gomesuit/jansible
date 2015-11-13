package jansible.web.project.task;

import java.util.List;

import jansible.model.common.TaskKey;

public class TaskDetailForm extends TaskKey{
	private String description;
	private List<TaskParameter> taskParameterList;
	
	public TaskDetailForm(){}
	
	public TaskDetailForm(TaskKey taskKey){
		super(taskKey, taskKey.getTaskId());
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
