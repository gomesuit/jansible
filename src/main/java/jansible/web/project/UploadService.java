package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.model.common.FileKey;
import jansible.model.common.TemplateKey;
import jansible.web.project.form.GeneralFileForm;
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
		jansibleFiler.uploadFile(file, filePath);
	}
	
	public void templateUpload(UploadForm form){
		String templateDir = jansibleFiler.getTemplateDirName(form);
		upload(form, templateDir);
	}
	
	public void fileUpload(UploadForm form){
		String fileDir = jansibleFiler.getFileDirName(form);
		upload(form, fileDir);
	}
	
	public void deleteTemplate(GeneralFileForm form){
		TemplateKey templateKey = new TemplateKey(form.getProjectName(), form.getRoleName(), form.getFileName());
		String filePath = jansibleFiler.getTemplatePath(templateKey);
		jansibleFiler.deleteFile(filePath);
	}
	
	public void deleteFile(GeneralFileForm form){
		FileKey fileKey = new FileKey(form.getProjectName(), form.getRoleName(), form.getFileName());
		String filePath = jansibleFiler.getFilePath(fileKey);
		jansibleFiler.deleteFile(filePath);
	}
}
