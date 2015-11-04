package jansible.model.database;

import jansible.model.common.TemplateKey;

public class DbTemplate extends TemplateKey{
	
	public DbTemplate(){}

	public DbTemplate(String projectName, String roleName, String templateName) {
		super(projectName, roleName, templateName);
	}
}
