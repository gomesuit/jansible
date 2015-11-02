package jansible.web.project.form;

import jansible.model.common.RoleRelationKey;

public class RoleRelationForm extends RoleRelationKey{
	private int sort;
	
	public RoleRelationForm(){}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
