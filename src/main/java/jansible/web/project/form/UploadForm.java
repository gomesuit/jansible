package jansible.web.project.form;

import jansible.model.common.RoleKey;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm extends RoleKey{
	private MultipartFile file;
	
	public UploadForm(){}
	
	public UploadForm(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
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
