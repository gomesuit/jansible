package jansible.model.database;

import jansible.model.common.RoleKey;
import jansible.model.common.TemplateKey;

public class DbTemplate extends TemplateKey{
	
	public DbTemplate(){}

	public DbTemplate(RoleKey roleKey){
		super(roleKey);
	}

	public DbTemplate(TemplateKey templateKey) {
		super(templateKey, templateKey.getTemplateName());
	}
}
