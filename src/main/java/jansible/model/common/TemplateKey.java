package jansible.model.common;

public class TemplateKey extends RoleKey{
	private String templateName;
	
	public TemplateKey(){}

	public TemplateKey(String projectName, String roleName, String templateName) {
		super(projectName, roleName);
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
