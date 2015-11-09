package jansible.model.common;

public class RoleRelationKey extends ServiceGroupKey{
	private String roleName;
	
	public RoleRelationKey(){}
	
	public RoleRelationKey(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}
	
	public RoleRelationKey(ServiceGroupKey serviceGroupKey, String roleName){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
