package jansible.web.project.role;

import jansible.model.common.RoleKey;
import jansible.model.common.TaskKey;

public class TaskOrderForm extends TaskKey{
	private TaskOrderType orderType;
	
	public TaskOrderForm(){}

	public TaskOrderForm(RoleKey roleKey){
		super(roleKey);
	}

	public TaskOrderForm(TaskKey taskKey){
		super(taskKey, taskKey.getTaskId());
	}

	public TaskOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(TaskOrderType orderType) {
		this.orderType = orderType;
	}
}
