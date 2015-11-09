package jansible.model.common;

public class TaskKey extends RoleKey{
	private Integer taskId;
	
	public TaskKey(){}

	public TaskKey(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}

	public TaskKey(RoleKey roleKey, Integer taskId) {
		this(roleKey);
		this.taskId = taskId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
}
