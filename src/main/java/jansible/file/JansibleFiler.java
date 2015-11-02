package jansible.file;

import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class JansibleFiler {
	private final String ROOT_PATH = "D:/temp/";
	private final String PATH_SEPARATOR = "/";
	
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
