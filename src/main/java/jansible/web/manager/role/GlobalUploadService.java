package jansible.web.manager.role;

import jansible.file.JansibleFiler;
import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleTemplateKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GlobalUploadService {
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
		jansibleFiler.mkGlobalRoleTemplateDir(form);
		String templateDir = jansibleFiler.getGlobalRoleTemplateDirName(form);
		String templatePath = templateDir + "/" + getOriginalFilename(form) + ".j2";
		upload(form, templatePath);
	}
	
	public void fileUpload(UploadForm form){
		jansibleFiler.mkGlobalRoleFileDir(form);
		String fileDir = jansibleFiler.getGlobalRoleFileDirName(form);
		String filePath = fileDir + "/" + getOriginalFilename(form);
		upload(form, filePath);
	}
	
	public void deleteTemplate(GeneralFileForm form){
		GlobalRoleTemplateKey templateKey = new GlobalRoleTemplateKey(form);
		templateKey.setTemplateName(form.getFileName());
		String filePath = jansibleFiler.getGlobalRoleTemplatePath(templateKey);
		System.out.println(filePath);
		jansibleFiler.deleteFile(filePath);
	}
	
	public void deleteFile(GeneralFileForm form){
		GlobalRoleFileKey globalRoleFileKey = new GlobalRoleFileKey(form);
		globalRoleFileKey.setFileName(form.getFileName());
		String filePath = jansibleFiler.getGlobalRoleFilePath(globalRoleFileKey);
		jansibleFiler.deleteFile(filePath);
	}
}
