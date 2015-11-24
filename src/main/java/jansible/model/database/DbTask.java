package jansible.model.database;

import jansible.model.common.TaskKey;

public class DbTask extends TaskKey implements InterfaceDbSort{
	private String moduleName;
	private String description;
	private int sort;
	
	public DbTask(){}

	public DbTask(TaskKey taskKey) {
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
