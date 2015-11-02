package jansible.model.common;

public class RoleRelationKey extends ServiceGroupKey{
	private String roleName;
	
	public RoleRelationKey(){}

	public RoleRelationKey(String projectName, String environmentName, String groupName, String roleName) {
		super(projectName, environmentName, groupName);
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
