package jansible.model.common;

public class GlobalTaskKey extends GlobalRoleKey{
	private Integer taskId;
	
	public GlobalTaskKey(){}

	public GlobalTaskKey(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public GlobalTaskKey(GlobalRoleKey key, Integer taskId) {
		this(key);
		this.taskId = taskId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlobalTaskKey other = (GlobalTaskKey) obj;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}
	
}
