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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
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
		RoleRelationKey other = (RoleRelationKey) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}
}
