package jansible.model.common;

public class GlobalRoleRelationKey extends ProjectKey{
	private String roleName;
	
	public GlobalRoleRelationKey(){}

	public GlobalRoleRelationKey(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}
	
	public GlobalRoleRelationKey(ProjectKey projectKey, String roleName) {
		this(projectKey);
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
