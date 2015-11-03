package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.web.project.form.TemplateUploadForm;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	private void fileUpload(MultipartFile file, String filePath) {
		if (!file.isEmpty()) {
			try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))){
				byte[] bytes = file.getBytes();
				stream.write(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void templateUpload(TemplateUploadForm form){
		MultipartFile file = form.getFile();
		String name = form.getName();
		String templateDir = jansibleFiler.getTemplateDirName(form);
		String filePath = templateDir + "/" + name;
		fileUpload(file, filePath);
	}
}
