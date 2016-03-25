package jansible.web.project.group.form;

public class Role {
	private String name;
	private RoleType roleType;
	
	public Role(String name, RoleType roleType) {
		this.name = name;
		this.roleType = roleType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
