package jansible.model.common;

public class TaskConditionalKey extends TaskKey{
	private String conditionalName;
	
	public TaskConditionalKey(){}

	public TaskConditionalKey(TaskKey taskKey){
		super(taskKey, taskKey.getTaskId());
	}
	
	public TaskConditionalKey(TaskKey taskKey, String conditionalName){
		this(taskKey);
		this.conditionalName = conditionalName;
	}

	public String getConditionalName() {
		return conditionalName;
	}

	public void setConditionalName(String conditionalName) {
		this.conditionalName = conditionalName;
	}
}
