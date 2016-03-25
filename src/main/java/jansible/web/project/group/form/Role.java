package jansible.web.project.group.form;

public class Role {
	private String name;
	private boolean global;
	
	public Role(String name, boolean global) {
		super();
		this.name = name;
		this.global = global;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setGlobal(boolean global) {
		this.global = global;
	}
}
