package jansible.web.project.group;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServiceGroupKey;

public class RoleRelationSortForm extends RoleRelationKey{
	private RoleRelationSortType SortType;
	
	public RoleRelationSortForm(){}

	public RoleRelationSortForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey);
	}

	public RoleRelationSortForm(RoleRelationKey roleRelationKey){
		super(roleRelationKey, roleRelationKey.getRoleName());
	}

	public RoleRelationSortType getSortType() {
		return SortType;
	}

	public void setSortType(RoleRelationSortType sortType) {
		SortType = sortType;
	}
}
