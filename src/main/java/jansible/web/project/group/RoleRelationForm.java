package jansible.web.project.group;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServiceGroupKey;

public class RoleRelationForm extends RoleRelationKey{
	private int sort;
	
	public RoleRelationForm(){}

	public RoleRelationForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey);
	}

	public RoleRelationForm(RoleRelationKey roleRelationKey){
		super(roleRelationKey, roleRelationKey.getRoleName());
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
