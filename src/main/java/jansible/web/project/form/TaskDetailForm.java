package jansible.web.project.form;

import java.util.List;

import jansible.model.common.TaskKey;

public class TaskDetailForm extends TaskKey{
	private List<TaskParameter> taskParameterList;

	public List<TaskParameter> getTaskParameterList() {
		return taskParameterList;
	}
	public void setTaskParameterList(List<TaskParameter> taskParameterList) {
		this.taskParameterList = taskParameterList;
	}
}
