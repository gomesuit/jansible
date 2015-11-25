package jansible.model.common;

public class GlobalTaskConditionalKey extends GlobalTaskKey{
	private String conditionalName;
	
	public GlobalTaskConditionalKey(){}

	public GlobalTaskConditionalKey(GlobalTaskKey key){
		super(key, key.getTaskId());
	}
	
	public GlobalTaskConditionalKey(GlobalTaskKey key, String conditionalName){
		this(key);
		this.conditionalName = conditionalName;
	}

	public String getConditionalName() {
		return conditionalName;
	}

	public void setConditionalName(String conditionalName) {
		this.conditionalName = conditionalName;
	}
}
