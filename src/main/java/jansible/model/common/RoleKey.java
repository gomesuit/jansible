package jansible.model.common;

public class RoleKey extends ProjectKey{
	private String roleName;
	
	public RoleKey(){}
	
	public RoleKey(String projectName, String roleName) {
		super(projectName);
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
