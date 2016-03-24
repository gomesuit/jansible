package jansible.web.manager.role.form;

import jansible.model.common.GlobalRoleKey;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm extends GlobalRoleKey{
	private MultipartFile file;
	
	public UploadForm(){}
	
	public UploadForm(GlobalRoleKey key){
		super(key.getRoleName());
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFileName() {
		return file.getOriginalFilename();
	}
}
