package jansible.file;

import jansible.model.common.FileKey;
import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTemplateKey;
import jansible.model.common.Group;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.TemplateKey;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JansibleFiler {
	
	@Value("${jansible.root.path}")
	private String ROOT_PATH;
	private String PROJECT_PATH_PREFIX = "project";
	private String GLOBAL_PATH_PREFIX = "global";
	private final String PATH_SEPARATOR = "/";
	private final String DEFAULT_YAML_NAME = "main.yml";
	
	public void writeGlobalRoleYaml(GlobalRoleKey key) {
		writeGlobalRoleYaml(key, "Hello");
	}

	public void writeGlobalRoleYaml(GlobalRoleKey key, String yaml) {
		String filePath = getGlobalTaskYamlPath(key);
		writeFile(filePath, yaml);
	}

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
	
	public void writeGlobalRoleVariableYaml(GlobalRoleKey key, String yaml) {
		String filePath = getGlobalRoleVariableYamlPath(key);
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
	
	private String getGlobalTaskYamlPath(GlobalRoleKey key) {
		String taskDirName = getGlobalRoleTaskDirName(key);
		String filePath = taskDirName + PATH_SEPARATOR + DEFAULT_YAML_NAME;
		return filePath;
	}

	private String getRoleVariableYamlPath(RoleKey roleKey){
		String dirName = getRoleVariableDirName(roleKey);
		String filePath = dirName + PATH_SEPARATOR + DEFAULT_YAML_NAME;
		return filePath;
	}
	
	private String getGlobalRoleVariableYamlPath(GlobalRoleKey key) {
		String dirName = getGlobalRoleVariableDirName(key);
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
		return "E-" + environmentName + "_G-" + groupName;
	}
	
	public String getServerStartYamlName(ServerRelationKey key){
		String environmentName = key.getEnvironmentName();
		String groupName = key.getGroupName();
		String serverName = key.getServerName();
		return getServerStartYamlName(environmentName, groupName, serverName);
	}
	
	public String getServerStartYamlName(String environmentName, String groupName, String ServerName){
		return getGroupName(environmentName, groupName) + "_S-" + ServerName;
	}

	public void writeFile(String filePath, String content){
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
	
	public void deleteGlobelRoleDir(GlobalRoleKey key) {
		String dirName = getGlobalRoleDirName(key);
		deleteDirByRecursive(dirName);
	}

	public void deleteProjectDir(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		deleteDirByRecursive(dirName);
	}
	
	public void deleteRolesDir(ProjectKey projectKey){
		String dirName = getRolesDirName(projectKey);
		deleteDirByRecursive(dirName);
	}
	
	public void deleteAllStartYamlfile(ProjectKey projectKey){
		deleteAllStartYamlfile(getProjectDirName(projectKey));
	}
	
	private void deleteAllStartYamlfile(String dirPath){
		File root = new File(dirPath);
		File[] fileList = root.listFiles();
		for(File file : fileList){
			if(isYamlFile(file)){
				deleteFile(file);
			}
		}
	}
	
	private boolean isYamlFile(File file) {
		String fileName = file.getName();
		if(fileName.endsWith(".yml")){
			return true;
		}else{
			return false;
		}
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
			deleteFile(root);
		}else{
			// ディレクトリの場合、再帰する
			File[] fileList = root.listFiles();
			for(File file : fileList){
				deleteDirByRecursive(file);
			}
			deleteFile(root);
		}
	}
	
	private void mkDir(String dirName){
		File newfile = new File(dirName);
		if(!newfile.exists()){
			newfile.mkdirs();
		}
	}

	public void mkProjectDir(ProjectKey projectKey){
		mkDir(getProjectDirName(projectKey));
	}

	public void mkHostVariableDir(ProjectKey projectKey){
		mkDir(getHostVariableDirName(projectKey));
	}

	public void deleteHostVariableDir(ProjectKey projectKey){
		deleteDirByRecursive(getHostVariableDirName(projectKey));
	}

	public void mkGroupVariableDir(ProjectKey projectKey){
		mkDir(getGroupVariableDirName(projectKey));
	}

	public void deleteGroupVariableDir(ProjectKey projectKey){
		deleteDirByRecursive(getGroupVariableDirName(projectKey));
	}

	public void mkRoleDir(RoleKey roleKey){
		mkDir(getRoleDirName(roleKey));
	}

	public void mkGlobalRoleDir(GlobalRoleKey key){
		mkDir(getGlobalRoleDirName(key));
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

	public void mkGlobalRoleTaskDir(GlobalRoleKey key) {
		mkDir(getGlobalRoleTaskDirName(key));
	}

	public void mkGlobalRoleTemplateDir(GlobalRoleKey key) {
		mkDir(getGlobalRoleTemplateDirName(key));
	}

	public void mkGlobalRoleFileDir(GlobalRoleKey key) {
		mkDir(getGlobalRoleFileDirName(key));
	}

	public void mkGlobalRoleVariableDir(GlobalRoleKey key) {
		mkDir(getGlobalRoleVariableDirName(key));
	}

	public void mkRoleVariableDir(RoleKey roleKey){
		String dirName = getRoleVariableDirName(roleKey);
		mkDir(dirName);
	}
	
	public String getSystemDirName(){
		return ROOT_PATH;
	}

	public String getProjectDirName(ProjectKey projectKey) {
		return getSystemDirName() + PROJECT_PATH_PREFIX + PATH_SEPARATOR + projectKey.getProjectName();
	}
	
	private String getRoleDirName(RoleKey roleKey) {
		String dirName = getRolesDirName(roleKey);
		dirName += PATH_SEPARATOR;
		dirName += roleKey.getRoleName();
		return dirName;
	}
	
	public String getGlobalRoleDirName(GlobalRoleKey key) {
		return ROOT_PATH + GLOBAL_PATH_PREFIX + PATH_SEPARATOR + key.getRoleName();
	}
	
	private String getGlobalRoleTaskDirName(GlobalRoleKey key) {
		String roleDirName = getGlobalRoleDirName(key);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "tasks";
		return roleDirName;
	}

	public String getGlobalRoleTemplateDirName(GlobalRoleKey key) {
		String roleDirName = getGlobalRoleDirName(key);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "templates";
		return roleDirName;
	}

	public String getGlobalRoleFileDirName(GlobalRoleKey key) {
		String roleDirName = getGlobalRoleDirName(key);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "files";
		return roleDirName;
	}

	private String getGlobalRoleVariableDirName(GlobalRoleKey key) {
		String roleDirName = getGlobalRoleDirName(key);
		roleDirName += PATH_SEPARATOR;
		roleDirName += "vars";
		return roleDirName;
	}

	private String getRolesDirName(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		dirName += PATH_SEPARATOR;
		dirName += "roles";
		return dirName;
	}
	
	private String getHostVariableDirName(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		dirName += PATH_SEPARATOR;
		dirName += "host_vars";
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
		//roleDirName += "vars";
		roleDirName += "defaults";
		return roleDirName;
	}
	
	public String getTemplatePath(TemplateKey templateKey) {
		String templateDirName = getTemplateDirName(templateKey);
		templateDirName += PATH_SEPARATOR;
		templateDirName += templateKey.getTemplateName();
		return templateDirName;
	}
	
	public String getGlobalRoleTemplatePath(GlobalRoleTemplateKey key) {
		String templateDirName = getGlobalRoleTemplateDirName(key);
		templateDirName += PATH_SEPARATOR;
		templateDirName += key.getTemplateName();
		return templateDirName;
	}

	public String getFilePath(FileKey fileKey) {
		String fileDirName = getFileDirName(fileKey);
		fileDirName += PATH_SEPARATOR;
		fileDirName += fileKey.getFileName();
		return fileDirName;
	}
	
	public String getGlobalRoleFilePath(GlobalRoleFileKey key) {
		String fileDirName = getGlobalRoleFileDirName(key);
		fileDirName += PATH_SEPARATOR;
		fileDirName += key.getFileName();
		return fileDirName;
	}
	
	private String getHostsFilePath(ProjectKey projectKey){
		String dirName = getProjectDirName(projectKey);
		dirName += PATH_SEPARATOR;
		dirName += getHostsFileName();
		return dirName;
	}
	
	private String getServerHostsFilePath(ServerKey key){
		String dirName = getProjectDirName(key);
		dirName += PATH_SEPARATOR;
		dirName += getServerHostsFileName(key);
		return dirName;
	}
	
	public String getHostsFileName(){
		return "hosts";
	}
	
	public String getServerHostsFileName(ServerKey key){
		return key.getServerName() + "_" + getHostsFileName();
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
	
	public void deleteHostsFile(ProjectKey projectKey){
		for(File file : getHostsFilePathList(projectKey)){
			deleteFile(file);
		}
	}
	
	private List<File> getHostsFilePathList(ProjectKey key) {
		String dirName = getProjectDirName(key);
		File file = new File(dirName);
		
		List<File> fileList = new ArrayList<>();
		for(File item : file.listFiles()){
			if(item.getName().endsWith("hosts")){
				fileList.add(item);
			}
		}
		return fileList;
	}

	public void writeServerHostsFile(ServerKey key, String hostsFileContent){
		String hostsFilePath = getServerHostsFilePath(key);
		writeFile(hostsFilePath, hostsFileContent);
	}
	
	public void deleteServerHostsFile(ServerKey key){
		deleteDirByRecursive(getServerHostsFilePath(key));
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
	
	private void deleteFile(String filePath){
		File file = new File(filePath);
		deleteFile(file);
	}
	
	private void deleteFile(File file){
		if(file.exists()){
			file.delete();
		}
	}

	public void deleteRoleVariableYaml(RoleKey roleKey) {
		String filePath = getRoleVariableYamlPath(roleKey);
		deleteFile(filePath);
	}

	public void deleteGlobalRoleTemplate(GlobalRoleTemplateKey templateKey) {
		String filePath = getGlobalRoleTemplatePath(templateKey);
		deleteFile(filePath);
	}

	public void deleteGlobalRoleFilePath(GlobalRoleFileKey globalRoleFileKey) {
		String filePath = getGlobalRoleFilePath(globalRoleFileKey);
		deleteFile(filePath);
	}

	public void deleteTemplate(TemplateKey templateKey) {
		String filePath = getTemplatePath(templateKey);
		deleteFile(filePath);
	}

	public void deleteRoleFile(FileKey fileKey) {
		String filePath = getFilePath(fileKey);
		deleteFile(filePath);
	}
}
