package jansible.web.project.role;

import jansible.model.common.RoleKey;
import jansible.model.common.TaskKey;

public class TaskForm extends TaskKey{
	private String moduleName;
	private String description;
	
	public TaskForm(){}

	public TaskForm(RoleKey roleKey){
		super(roleKey);
	}

	public TaskForm(TaskKey taskKey){
		super(taskKey, taskKey.getTaskId());
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
