package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import jansible.file.JansibleFiler;
import jansible.mapper.ProjectMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.FileKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.Target;
import jansible.model.common.TaskKey;
import jansible.model.common.TemplateKey;
import jansible.model.common.VariableKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbFile;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbServer;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.database.DbTemplate;
import jansible.model.database.DbVariable;
import jansible.web.project.form.EnvironmentForm;
import jansible.web.project.form.GeneralFileForm;
import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.RoleRelationForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServiceGroupForm;
import jansible.web.project.form.TaskDetailForm;
import jansible.web.project.form.TaskForm;
import jansible.web.project.form.TaskParameter;
import jansible.web.project.form.UploadForm;
import jansible.web.project.form.VariableForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private JansibleFiler jansibleFiler;

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
	}

	public List<DbEnvironment> getEnvironmentList(String projectName){
		ProjectKey projectKey = new ProjectKey(projectName);
		return projectMapper.selectEnvironmentList(projectKey);
	}

	public List<DbServiceGroup> getServiceGroupList(String projectName, String environmentName){
		EnvironmentKey environmentKey = new EnvironmentKey(projectName, environmentName);
		return projectMapper.selectServiceGroupList(environmentKey);
	}

	public List<DbServer> getServerList(String projectName, String environmentName, String groupName){
		ServiceGroupKey serviceGroupKey = new ServiceGroupKey(projectName, environmentName, groupName);
		return projectMapper.selectServerList(serviceGroupKey);
	}

	public List<DbRole> getRoleList(String projectName){
		DbProject dbProject = new DbProject(projectName);
		return projectMapper.selectRoleList(dbProject);
	}
	
	public List<DbTask> getTaskList(String projectName, String roleName){
		RoleKey roleKey = new RoleKey(projectName, roleName);
		return projectMapper.selectTaskList(roleKey);
	}
	
	public List<DbFile> getDbFileList(String projectName, String roleName){
		RoleKey roleKey = new RoleKey(projectName, roleName);
		return projectMapper.selectDbFileList(roleKey);
	}
	
	public List<DbTemplate> getDbTemplateList(String projectName, String roleName){
		RoleKey roleKey = new RoleKey(projectName, roleName);
		return projectMapper.selectDbTemplateList(roleKey);
	}
	
	public List<DbVariable> getDbVariableList(String projectName, Target target, String targetName){
		VariableKey variableKey = new VariableKey();
		variableKey.setProjectName(projectName);
		variableKey.setTarget(target);
		variableKey.setTargetName(targetName);
		return projectMapper.selectDbVariableList(variableKey);
	}
	
	public DbTask getTask(String projectName, String roleName, Integer taskId){
		TaskKey taskKey = new TaskKey(projectName, roleName, taskId);
		return projectMapper.selectTask(taskKey);
	}
	
	public List<DbTaskDetail> getTaskDetailList(String projectName, String roleName, Integer taskId){
		TaskKey taskKey = new TaskKey(projectName, roleName, taskId);
		return projectMapper.selectTaskDetailList(taskKey);
	}
	
	public List<DbRoleRelation> getRoleRelationList(String projectName, String environmentName, String groupName){
		ServiceGroupKey serviceGroupKey = new ServiceGroupKey(projectName, environmentName, groupName);
		return projectMapper.selectDbRoleRelationList(serviceGroupKey);
	}


	public void registProject(ProjectForm form) {
		DbProject dbProject = new DbProject(form.getProjectName());
		projectMapper.insertProject(dbProject);
		jansibleFiler.mkProjectDir(dbProject);
	}
	
	public void registEnvironment(EnvironmentForm form) {
		DbEnvironment dbEnvironment = new DbEnvironment(form.getProjectName(), form.getEnvironmentName());
		projectMapper.insertEnvironment(dbEnvironment);;
	}
	
	public void registServiceGroup(ServiceGroupForm form) {
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form.getProjectName(), form.getEnvironmentName(), form.getGroupName());
		projectMapper.insertServiceGroup(dbServiceGroup);
	}
	
	public void registServer(ServerForm form) {
		DbServer dbServer = createDbServer(form);
		projectMapper.insertServer(dbServer);
	}
	
	public void registRole(RoleForm form) {
		DbRole dbRole = createDbRole(form);
		projectMapper.insertRole(dbRole);
		jansibleFiler.mkRoleDir(dbRole);
		jansibleFiler.mkRoleTaskDir(dbRole);
		jansibleFiler.mkRoleTemplateDir(dbRole);
		jansibleFiler.mkRoleFileDir(dbRole);
		jansibleFiler.writeRoleYaml(dbRole);
	}
	
	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		projectMapper.insertTask(dbTask);
	}
	
	public void updateTask(TaskDetailForm form) {
		DbTask dbTask = createDbTask(form);
		projectMapper.updateTask(dbTask);
	}
	
	public void registTaskDetail(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
			projectMapper.insertTaskDetail(dbTaskDetail);
		}
	}
	
	public void registRoleRelationDetail(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = createDbRoleRelation(form);
		projectMapper.insertDbRoleRelation(dbRoleRelation);
	}
	
	private DbRoleRelation createDbRoleRelation(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = new DbRoleRelation();
		dbRoleRelation.setProjectName(form.getProjectName());
		dbRoleRelation.setEnvironmentName(form.getEnvironmentName());
		dbRoleRelation.setGroupName(form.getGroupName());
		dbRoleRelation.setRoleName(form.getRoleName());
		dbRoleRelation.setSort(form.getSort());
		return dbRoleRelation;
	}

	private List<DbTaskDetail> createDbTaskDetailList(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = new ArrayList<>();
		List<TaskParameter> taskParameterList = form.getTaskParameterList();
		for(TaskParameter taskParameter : taskParameterList){
//			if(StringUtils.isBlank(taskParameter.getParameterValue())){
//				continue;
//			}
			
			DbTaskDetail dbTaskDetail = createDbTaskDetail(form.getTaskId(), form.getProjectName(), form.getRoleName(), taskParameter);
			dbTaskDetailList.add(dbTaskDetail);
		}
		return dbTaskDetailList;
	}

	private DbTaskDetail createDbTaskDetail(Integer taskId, String projectName, String roleName, TaskParameter taskParameter) {
		DbTaskDetail dbTaskDetail = new DbTaskDetail();
		dbTaskDetail.setTaskId(taskId);
		dbTaskDetail.setProjectName(projectName);
		dbTaskDetail.setRoleName(roleName);
		dbTaskDetail.setParameterName(taskParameter.getParameterName());
		dbTaskDetail.setParameterValue(taskParameter.getParameterValue());
		return dbTaskDetail;
	}

	private DbTask createDbTask(TaskForm form) {
		DbTask dbTask = new DbTask();
		dbTask.setProjectName(form.getProjectName());
		dbTask.setRoleName(form.getRoleName());
		dbTask.setModuleName(form.getModuleName());
		dbTask.setDescription(form.getDescription());
		dbTask.setFreeForm(form.getFreeForm());
		dbTask.setSort(form.getSort());
		return dbTask;
	}

	private DbTask createDbTask(TaskDetailForm form) {
		DbTask dbTask = new DbTask();
		dbTask.setTaskId(form.getTaskId());
		dbTask.setProjectName(form.getProjectName());
		dbTask.setRoleName(form.getRoleName());
		dbTask.setDescription(form.getDescription());
		return dbTask;
	}

	private DbServer createDbServer(ServerForm form){
		return new DbServer(form.getProjectName(), form.getEnvironmentName(), form.getGroupName(), form.getServerName());
	}
	
	private DbRole createDbRole(RoleForm form){	
		return new DbRole(form.getProjectName(), form.getRoleName());
	}
	
	public void registFile(UploadForm form) {
		DbFile dbFile = createDbFile(form);
		projectMapper.insertDbFile(dbFile);
	}

	private DbFile createDbFile(UploadForm form) {
		MultipartFile file = form.getFile();
		DbFile dbFile = new DbFile(form.getProjectName(), form.getRoleName(), file.getOriginalFilename());
		return dbFile;
	}
	
	public void registTemplate(UploadForm form) {
		DbTemplate dbTemplate = createDbTemplate(form);
		projectMapper.insertDbTemplate(dbTemplate);
	}

	private DbTemplate createDbTemplate(UploadForm form) {
		MultipartFile file = form.getFile();
		DbTemplate dbTemplate = new DbTemplate(form.getProjectName(), form.getRoleName(), file.getOriginalFilename());
		return dbTemplate;
	}
	
	public void deleteFile(GeneralFileForm form){
		FileKey fileKey = new FileKey(form.getProjectName(), form.getRoleName(), form.getFileName());
		projectMapper.deleteDbFile(fileKey);
	}
	
	public void deleteTemplate(GeneralFileForm form){
		TemplateKey templateKey = new TemplateKey(form.getProjectName(), form.getRoleName(), form.getFileName());
		projectMapper.deleteDbTemplate(templateKey);
	}
	
	public void registVariable(VariableForm form) {
		DbVariable dbVariable = createDbVariable(form);
		projectMapper.insertDbVariable(dbVariable);
	}

	private DbVariable createDbVariable(VariableForm form) {
		DbVariable dbVariable = new DbVariable();
		dbVariable.setProjectName(form.getProjectName());
		dbVariable.setTarget(form.getTarget());
		dbVariable.setTargetName(form.getTargetName());
		dbVariable.setVariableName(form.getVariableName());
		dbVariable.setValue(form.getValue());
		return dbVariable;
	}
}
