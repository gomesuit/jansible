package jansible.web.project.group.form;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServiceGroupKey;

public class RoleRelationOrderForm extends RoleRelationKey{
	private RoleRelationOrderType OrderType;
	
	public RoleRelationOrderForm(){}

	public RoleRelationOrderForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey);
	}

	public RoleRelationOrderForm(RoleRelationKey roleRelationKey){
		super(roleRelationKey, roleRelationKey.getRoleName());
	}

	public RoleRelationOrderType getOrderType() {
		return OrderType;
	}

	public void setOrderType(RoleRelationOrderType orderType) {
		OrderType = orderType;
	}
}
