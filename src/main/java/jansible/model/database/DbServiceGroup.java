package jansible.model.database;

import jansible.model.common.ServiceGroupKey;

public class DbServiceGroup extends ServiceGroupKey{
	public DbServiceGroup(){}

	public DbServiceGroup(ServiceGroupKey serviceGroupKey) {
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}
}
