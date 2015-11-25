package jansible.model.database;

import jansible.model.common.GlobalTaskDetailKey;
import jansible.model.common.GlobalTaskKey;

public class DbGlobalTaskDetail extends GlobalTaskDetailKey{
	private String parameterValue;
	
	public DbGlobalTaskDetail(){}

	public DbGlobalTaskDetail(GlobalTaskKey key) {
		super(key);
	}

	public DbGlobalTaskDetail(GlobalTaskDetailKey key) {
		super(key, key.getParameterName());
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
