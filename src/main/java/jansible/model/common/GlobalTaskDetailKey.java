package jansible.model.common;

public class GlobalTaskDetailKey extends GlobalTaskKey{
	private String parameterName;
	
	public GlobalTaskDetailKey(){}

	public GlobalTaskDetailKey(GlobalTaskKey key){
		super(key, key.getTaskId());
	}

	public GlobalTaskDetailKey(GlobalTaskKey key, String parameterName) {
		this(key);
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
}
