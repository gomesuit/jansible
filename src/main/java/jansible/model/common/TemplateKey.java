package jansible.model.common;

public class TemplateKey extends RoleKey{
	private String templateName;
	
	public TemplateKey(){}

	public TemplateKey(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}

	public TemplateKey(RoleKey roleKey, String templateName) {
		this(roleKey);
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
