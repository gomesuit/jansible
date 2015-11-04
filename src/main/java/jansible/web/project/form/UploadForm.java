package jansible.web.project.form;

import jansible.model.common.RoleKey;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm extends RoleKey{
	private MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
