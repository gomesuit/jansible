package jansible.model.database;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTemplateKey;

public class DbGlobalRoleTemplate extends GlobalRoleTemplateKey{
	
	public DbGlobalRoleTemplate(){}

	public DbGlobalRoleTemplate(GlobalRoleKey key){
		super(key);
	}

	public DbGlobalRoleTemplate(GlobalRoleTemplateKey key) {
		super(key, key.getTemplateName());
	}
}
