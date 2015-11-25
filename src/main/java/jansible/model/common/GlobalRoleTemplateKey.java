package jansible.model.common;

public class GlobalRoleTemplateKey extends GlobalRoleKey{
	private String templateName;
	
	public GlobalRoleTemplateKey(){}

	public GlobalRoleTemplateKey(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public GlobalRoleTemplateKey(GlobalRoleKey key, String templateName) {
		this(key);
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
