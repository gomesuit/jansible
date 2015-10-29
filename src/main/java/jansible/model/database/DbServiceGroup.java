package jansible.model.database;

import jansible.model.common.ServiceGroupKey;

public class DbServiceGroup extends ServiceGroupKey{
	public DbServiceGroup(){}

	public DbServiceGroup(String projectName, String environmentName, String groupName) {
		super(projectName, environmentName, groupName);
	}
}
