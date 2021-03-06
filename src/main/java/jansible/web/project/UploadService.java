package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.model.common.FileKey;
import jansible.model.common.TemplateKey;
import jansible.web.project.role.form.GeneralFileForm;
import jansible.web.project.role.form.UploadForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	private void upload(UploadForm form, String filePath){
		MultipartFile file = form.getFile();
		jansibleFiler.uploadFile(file, filePath);
	}
	
	private String getOriginalFilename(UploadForm form){
		MultipartFile file = form.getFile();
		String name = file.getOriginalFilename();
		return name;
	}
	
	public void templateUpload(UploadForm form){
		jansibleFiler.mkRoleTemplateDir(form);
		String templateDir = jansibleFiler.getTemplateDirName(form);
		String templatePath = templateDir + "/" + getOriginalFilename(form);
		upload(form, templatePath);
	}
	
	public void fileUpload(UploadForm form){
		jansibleFiler.mkRoleFileDir(form);
		String fileDir = jansibleFiler.getFileDirName(form);
		String filePath = fileDir + "/" + getOriginalFilename(form);
		upload(form, filePath);
	}
	
	public void deleteTemplate(GeneralFileForm form){
		TemplateKey templateKey = new TemplateKey(form);
		templateKey.setTemplateName(form.getFileName());
		jansibleFiler.deleteTemplate(templateKey);
	}
	
	public void deleteFile(GeneralFileForm form){
		FileKey fileKey = new FileKey(form);
		fileKey.setFileName(form.getFileName());
		jansibleFiler.deleteRoleFile(fileKey);
	}
}
