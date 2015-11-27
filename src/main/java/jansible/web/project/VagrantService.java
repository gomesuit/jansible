package jansible.web.project;

import java.io.File;

import jansible.file.JansibleFiler;
import jansible.template.JansibleTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VagrantService {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	private static final String VAGRANT_FILE_NAME = "Vagrantfile";
	
	public void saveVagrantFile(String destDirPath){
		String fileContent = JansibleTemplate.getString(VAGRANT_FILE_NAME);
		String filePath = destDirPath + File.separator + VAGRANT_FILE_NAME;
		
		jansibleFiler.writeFile(filePath, fileContent);
	}
}
