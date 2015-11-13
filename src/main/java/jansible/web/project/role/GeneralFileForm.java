package jansible.web.project.role;

import jansible.model.common.RoleKey;

public class GeneralFileForm extends RoleKey{
	private String fileName;
	
	public GeneralFileForm(){}
	
	public GeneralFileForm(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
