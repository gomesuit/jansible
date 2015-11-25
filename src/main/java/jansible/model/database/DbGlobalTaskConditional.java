package jansible.model.database;

import jansible.model.common.GlobalTaskConditionalKey;

public class DbGlobalTaskConditional extends GlobalTaskConditionalKey{
	private String conditionalValue;
	
	public DbGlobalTaskConditional(){}

	public DbGlobalTaskConditional(GlobalTaskConditionalKey key) {
		super(key, key.getConditionalName());
	}

	public String getConditionalValue() {
		return conditionalValue;
	}

	public void setConditionalValue(String conditionalValue) {
		this.conditionalValue = conditionalValue;
	}
}
