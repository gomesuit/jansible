package jansible.file;

import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class JansibleFiler {
	private final String ROOT_PATH = "D:/temp/";
	private final String PATH_SEPARATOR = "/";
	private final String DEFAULT_YAML_NAME = "main.yml";
	
	public void writeRoleYaml(RoleKey roleKey){
		writeRoleYaml(roleKey, "Hello");
	}
	
	public void writeRoleYaml(RoleKey roleKey, String yaml){
		String dirName = getDirName(roleKey);
		String filePath = ROOT_PATH + dirName + PATH_SEPARATOR + "tasks" + PATH_SEPARATOR + DEFAULT_YAML_NAME;
		writeFile(filePath, yaml);
	}
	
	private void writeFile(String filePath, String content){
		File file = new File(filePath);
		try(FileWriter filewriter = new FileWriter(file)){
			filewriter.write(content);
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	private void mkDir(String dirName){
		File newfile = new File(ROOT_PATH + dirName);
		newfile.mkdirs();
	}

	public void mkProjectDir(ProjectKey projectKey){
		mkDir(getDirName(projectKey));
	}

	public void mkRoleDir(RoleKey roleKey){
		mkDir(getDirName(roleKey));
	}

	public void mkRoleFileDir(RoleKey roleKey){
		String dirName = getDirName(roleKey);
		dirName += PATH_SEPARATOR;
		dirName += "file";
		mkDir(dirName);
	}

	public void mkRoleTemplateDir(RoleKey roleKey){
		String dirName = getDirName(roleKey);
		dirName += PATH_SEPARATOR;
		dirName += "template";
		mkDir(dirName);
	}

	public void mkRoleTaskDir(RoleKey roleKey){
		String dirName = getDirName(roleKey);
		dirName += PATH_SEPARATOR;
		dirName += "tasks";
		mkDir(dirName);
	}

	private String getDirName(ProjectKey projectKey) {
		return projectKey.getProjectName();
	}

	private String getDirName(RoleKey roleKey) {
		String dirName = getDirName((ProjectKey)roleKey);
		dirName += PATH_SEPARATOR;
		dirName += roleKey.getRoleName();
		return dirName;
	}
}
