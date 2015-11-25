package jansible.web.manager.role;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalTaskKey;

public class TaskOrderForm extends GlobalTaskKey{
	private TaskOrderType orderType;
	
	public TaskOrderForm(){}

	public TaskOrderForm(GlobalRoleKey key){
		super(key);
	}

	public TaskOrderForm(GlobalTaskKey key){
		super(key, key.getTaskId());
	}

	public TaskOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(TaskOrderType orderType) {
		this.orderType = orderType;
	}
}
