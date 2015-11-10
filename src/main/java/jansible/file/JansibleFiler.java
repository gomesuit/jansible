package jansible.file;

import jansible.model.common.FileKey;
import jansible.model.common.Group;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;
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
		String filePath = getTaskYamlPath(roleKey);
		writeFile(filePath, yaml);
	}
	
	public void writeRoleVariableYaml(RoleKey roleKey, String yaml){
		String filePath = getRoleVariableYamlPath(roleKey);
		writeFile(filePath, yaml);
	}
	
	public void writeHostVariableYaml(ServerKey serverKey, String yaml){
		String filePath = getHostVariableYamlPath(serverKey);
		writeFile(filePath, yaml);
	}
	
	public void writeGroupVariableYaml(ServiceGroupKey serviceGroupKey, String yaml){
		String filePath = getGroupVariableYamlPath(serviceGroupKey);
		writeFile(filePath, yaml);
	}
	
	private String getTaskYamlPath(RoleKey roleKey){
		String taskDirName = getTaskDirName(roleKey);
		String filePath = taskDirName + PATH_SEPARATOR + DEFAULT_YAML_NAME;
		return filePath;
	}
	
	private String getRoleVariableYamlPath(RoleKey roleKey){
		String dirName = getRoleVariableDirName(roleKey);
		String filePath = dirName + PATH_SEPARATOR + DEFAULT_YAML_NAME;
		return filePath;
	}
	
	private String getHostVariableYamlPath(ServerKey serverKey){
		String dirName = getHostVariableDirName(serverKey);
		String filePath = dirName + PATH_SEPARATOR + serverKey.getServerName();
		return filePath;
	}
	
	private String getGroupVariableYamlPath(ServiceGroupKey serviceGroupKey){
		String dirName = getGroupVariableDirName(serviceGroupKey);
		String filePath = dirName + PATH_SEPARATOR + getGroupName(serviceGroupKey);
		return filePath;
	}
	
	public String getGroupName(ServiceGroupKey serviceGroupKey) {
		String environmentName = serviceGroupKey.getEnvironmentName();
		String groupName = serviceGroupKey.getGroupName();
		return getGroupName(environmentName, groupName);
	}
	
	public String getGroupName(Group group) {
		String environmentName = group.getEnvironmentName();
		String groupName = group.getGroupName();
		return getGroupName(environmentName, groupName);
	}
	
	public String getGroupName(String environmentName, String groupName) {
		return environmentName + "_" + groupName;
	}

	private void writeFile(String filePath, String content){
		File file = new File(filePath);
		try(FileWriter filewriter = new FileWriter(file)){
			filewriter.write(content);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void deleteGroupVariableYaml(ServiceGroupKey serviceGroupKey){
		String filePath = getGroupVariableYamlPath(serviceGroupKey);
		deleteFile(filePath);
	}
	
	public void deleteHostVariableYaml(ServerKey serverKey){
		String filePath = getHostVariableYamlPath(serverKey);
		deleteFile(filePath);
	}
	
	public void deleteRoleDir(RoleKey roleKey){
		String dirName = getRoleDirName(roleKey);
		deleteDirByRecursive(dirName);
	}
	
	private void deleteDirByRecursive(String dirPath){
		File file = new File(dirPath);
		deleteDirByRecursive(file);
	}

	private void deleteDirByRecursive(File root){
		if(root == null) return;
		if(!root.exists()) return;
		
		if(root.isFile()){
			// ファイル削除
			if(root.exists() && !root.delete()){
				root.deleteOnExit();
			}
		}else{
			// ディレクトリの場合、再帰する
			File[] fileList = root.listFiles();
			for(File file : fileList){
				deleteDirByRecursive(file);
			}
			if(root.exists() && !root.delete()){
				root.deleteOnExit();
			}
		}
	}
	
	private void mkDir(String dirName){
		File newfile = new File(dirName);
		newfile.mkdirs();
	}

	public void mkProjectDir(ProjectKey projectKey){
		mkDir(getProjectDirName(projectKey));
	}

	public void mkHostVariableDir(ProjectKey projectKey){
		mkDir(getHostVariableDirName(projectKey));
	}

	public void mkGroupVariableDir(ProjectKey projectKey){
		mkDir(getGroupVariableDirName(projectKey));
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

	public void mkRoleVariableDir(RoleKey roleKey){
		String dirName = getRoleVariableDirName(roleKey);
		mkDir(dirName);
	}

	public String getProjectDirName(ProjectKey projectKey) {
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
	
	private String getHostVariableDirName(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		dirName += PATH_SEPARATOR;
		dirName += "hosr_vars";
		return dirName;
	}
	
	private String getGroupVariableDirName(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		dirName += PATH_SEPARATOR;
		dirName += "group_vars";
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
	
	public String getRoleVariableDirName(RoleKey roleKey) {
		String roleDirName = getRoleDirName(roleKey);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "vars";
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
	
	private String getHostsFilePath(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		dirName += PATH_SEPARATOR;
		dirName += "hosts";
		return dirName;
	}
	
	private String getStartYamlPath(ServiceGroupKey serviceGroupKey){
		String dirName = getProjectDirName(serviceGroupKey);
		String filePath = dirName + PATH_SEPARATOR + getGroupName(serviceGroupKey) + ".yml";
		return filePath;
	}
	
	public void writeStartYaml(ServiceGroupKey serviceGroupKey, String fileContent){
		String filePath = getStartYamlPath(serviceGroupKey);
		writeFile(filePath, fileContent);
	}
	
	public void writeHostsFile(ProjectKey projectKey, String hostsFileContent){
		String hostsFilePath = getHostsFilePath(projectKey);
		writeFile(hostsFilePath, hostsFileContent);
	}

	public void uploadFile(MultipartFile file, String filePath) {
		if (!file.isEmpty()) {
			try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))){
				byte[] bytes = file.getBytes();
				stream.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
	}
}
