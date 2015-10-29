package jansible.web.project.form;

public class RoleForm {
	private ProjectForm projectForm;
	private String roleName;
	
	public ProjectForm getProjectForm() {
		return projectForm;
	}
	public void setProjectForm(ProjectForm projectForm) {
		this.projectForm = projectForm;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
