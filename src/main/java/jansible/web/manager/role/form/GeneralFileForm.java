package jansible.web.manager.role.form;

import jansible.model.common.GlobalRoleKey;

public class GeneralFileForm extends GlobalRoleKey{
	private String fileName;
	
	public GeneralFileForm(){}
	
	public GeneralFileForm(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
