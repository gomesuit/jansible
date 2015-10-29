package jansible.model.common;

public class TaskKey extends RoleKey{
	private Integer taskId;
	
	public TaskKey(){}

	public TaskKey(String projectName, String roleName, Integer taskId) {
		super(projectName, roleName);
		this.taskId = taskId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
}
