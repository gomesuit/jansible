package jansible.model.database;

import jansible.model.common.GlobalTaskKey;

public class DbGlobalTask extends GlobalTaskKey implements InterfaceDbSort{
	private String moduleName;
	private String description;
	private int sort;
	
	public DbGlobalTask(){}

	public DbGlobalTask(GlobalTaskKey key) {
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
