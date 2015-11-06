package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.web.project.form.UploadForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	private void upload(UploadForm form, String dirName){
		MultipartFile file = form.getFile();
		String name = file.getOriginalFilename();
		String filePath = dirName + "/" + name;
		jansibleFiler.fileUpload(file, filePath);
	}
	
	public void templateUpload(UploadForm form){
		String templateDir = jansibleFiler.getTemplateDirName(form);
		upload(form, templateDir);
	}
	
	public void fileUpload(UploadForm form){
		String fileDir = jansibleFiler.getFileDirName(form);
		upload(form, fileDir);
	}
}
