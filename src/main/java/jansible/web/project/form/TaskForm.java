package jansible.web.project.form;

import jansible.model.common.TaskKey;

public class TaskForm extends TaskKey{
	private String moduleName;
	private String description;
	private String freeForm;
	private int sort;
	
	public TaskForm(){}

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

	public String getFreeForm() {
		return freeForm;
	}

	public void setFreeForm(String freeForm) {
		this.freeForm = freeForm;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
