package jansible.model.database;

import jansible.model.common.ServiceGroupKey;

public class DbServiceGroup extends ServiceGroupKey{
	private String description;
	
	public DbServiceGroup(){}

	public DbServiceGroup(ServiceGroupKey serviceGroupKey) {
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
