package jansible.model.database;

import jansible.model.common.EnvironmentKey;

public class DbEnvironment extends EnvironmentKey{
	
	public DbEnvironment(){}

	public DbEnvironment(EnvironmentKey environmentKey) {
		super(environmentKey, environmentKey.getEnvironmentName());
	}
}
