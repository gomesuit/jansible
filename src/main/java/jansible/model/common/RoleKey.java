package jansible.model.common;

public class RoleKey extends ProjectKey{
	private String roleName;
	
	public RoleKey(){}

	public RoleKey(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}
	
	public RoleKey(ProjectKey projectKey, String roleName) {
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
