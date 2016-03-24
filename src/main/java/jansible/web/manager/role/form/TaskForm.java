package jansible.web.manager.role.form;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalTaskKey;

public class TaskForm extends GlobalTaskKey{
	private String moduleName;
	private String description;
	
	public TaskForm(){}

	public TaskForm(GlobalRoleKey key){
		super(key);
	}

	public TaskForm(GlobalTaskKey key){
		super(key, key.getTaskId());
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
