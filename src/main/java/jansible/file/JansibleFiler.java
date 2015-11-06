package jansible.file;

import jansible.model.common.FileKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.TemplateKey;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JansibleFiler {
	private final String ROOT_PATH = "D:/temp/";
	private final String PATH_SEPARATOR = "/";
	private final String DEFAULT_YAML_NAME = "main.yml";
	
	public void writeRoleYaml(RoleKey roleKey){
		writeRoleYaml(roleKey, "Hello");
	}
	
	public void writeRoleYaml(RoleKey roleKey, String yaml){
		String taskDirName = getTaskDirName(roleKey);
		String filePath = taskDirName + PATH_SEPARATOR + DEFAULT_YAML_NAME;
		writeFile(filePath, yaml);
	}
	
	private void writeFile(String filePath, String content){
		File file = new File(filePath);
		try(FileWriter filewriter = new FileWriter(file)){
			filewriter.write(content);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void mkDir(String dirName){
		File newfile = new File(dirName);
		newfile.mkdirs();
	}

	public void mkProjectDir(ProjectKey projectKey){
		mkDir(getProjectDirName(projectKey));
	}

	public void mkRoleDir(RoleKey roleKey){
		mkDir(getRoleDirName(roleKey));
	}

	public void mkRoleFileDir(RoleKey roleKey){
		String dirName = getFileDirName(roleKey);
		mkDir(dirName);
	}

	public void mkRoleTemplateDir(RoleKey roleKey){
		String dirName = getTemplateDirName(roleKey);
		mkDir(dirName);
	}

	public void mkRoleTaskDir(RoleKey roleKey){
		String dirName = getTaskDirName(roleKey);
		mkDir(dirName);
	}

	private String getProjectDirName(ProjectKey projectKey) {
		return ROOT_PATH + projectKey.getProjectName();
	}
	
	private String getRoleDirName(RoleKey roleKey) {
		String dirName = getProjectDirName(roleKey);
		dirName += PATH_SEPARATOR;
		dirName += "roles";
		dirName += PATH_SEPARATOR;
		dirName += roleKey.getRoleName();
		return dirName;
	}
	
	private String getTaskDirName(RoleKey roleKey) {
		String roleDirName = getRoleDirName(roleKey);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "tasks";
		return roleDirName;
	}
	
	public String getTemplateDirName(RoleKey roleKey) {
		String roleDirName = getRoleDirName(roleKey);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "templates";
		return roleDirName;
	}
	
	public String getFileDirName(RoleKey roleKey) {
		String roleDirName = getRoleDirName(roleKey);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "files";
		return roleDirName;
	}
	
	public String getTemplatePath(TemplateKey templateKey) {
		String templateDirName = getTemplateDirName(templateKey);
		templateDirName += PATH_SEPARATOR;
		templateDirName += templateKey.getTemplateName();
		return templateDirName;
	}
	
	public String getFilePath(FileKey fileKey) {
		String fileDirName = getFileDirName(fileKey);
		fileDirName += PATH_SEPARATOR;
		fileDirName += fileKey.getFileName();
		return fileDirName;
	}

	public void fileUpload(MultipartFile file, String filePath) {
		if (!file.isEmpty()) {
			try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))){
				byte[] bytes = file.getBytes();
				stream.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
