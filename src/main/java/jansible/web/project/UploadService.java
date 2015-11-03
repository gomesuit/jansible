package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.web.project.form.UploadForm;

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
	
	private void upload(UploadForm form, String dirName){
		MultipartFile file = form.getFile();
		String name = form.getName();
		String filePath = dirName + "/" + name;
		fileUpload(file, filePath);
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
