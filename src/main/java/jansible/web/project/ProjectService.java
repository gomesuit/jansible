package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.git.JansibleGitter;
import jansible.jenkins.JenkinsBuilder;
import jansible.mapper.ApplyHistoryMapper;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.ProjectMapper;
import jansible.mapper.RoleMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.TaskMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;
import jansible.model.common.FileKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.RoleRelationKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerVariableKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.model.common.TaskKey;
import jansible.model.common.TemplateKey;
import jansible.model.database.DbApplyHistory;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbEnvironmentVariable;
import jansible.model.database.DbFile;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbRoleVariable;
import jansible.model.database.DbServer;
import jansible.model.database.DbServerVariable;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbServiceGroupVariable;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.database.DbTemplate;
import jansible.util.YamlDumper;
import jansible.web.project.environment.EnvironmentVariableForm;
import jansible.web.project.group.RoleRelationForm;
import jansible.web.project.group.ServiceGroupForm;
import jansible.web.project.group.ServiceGroupVariableForm;
import jansible.web.project.project.EnvironmentForm;
import jansible.web.project.project.JenkinsInfoForm;
import jansible.web.project.project.RoleForm;
import jansible.web.project.role.GeneralFileForm;
import jansible.web.project.role.RoleVariableForm;
import jansible.web.project.role.UploadForm;
import jansible.web.project.server.ServerForm;
import jansible.web.project.server.ServerVariableForm;
import jansible.web.project.task.TaskDetailForm;
import jansible.web.project.task.TaskForm;
import jansible.web.project.task.TaskParameter;
import jansible.web.project.top.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private EnvironmentMapper environmentMapper;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private ApplyHistoryMapper applyHistoryMapper;
	
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private YamlDumper yamlDumper;
	@Autowired
	private JansibleGitter jansibleGitter;
	@Autowired
	private JansibleHostsDumper jansibleHostsDumper;
	@Autowired
	private JenkinsBuilder jenkinsBuilder;
	@Autowired
	private FileService fileService;
	
	public void registJenkinsInfo(JenkinsInfoForm form){
		DbProject dbProject = new DbProject(form);
		dbProject.setJenkinsIpAddress(form.getJenkinsIpAddress());
		dbProject.setJenkinsPort(form.getJenkinsPort());
		dbProject.setJenkinsJobName(form.getJenkinsJobName());
		projectMapper.updateJenkinsInfo(dbProject);
	}

	public List<DbApplyHistory> getDbApplyHistoryList(ProjectKey projectKey){
		return applyHistoryMapper.selectDbApplyHistoryList(projectKey);
	}
	
	public DbApplyHistory getDbApplyHistory(ApplyHistoryKey applyHistoryKey){
		return applyHistoryMapper.selectDbApplyHistory(applyHistoryKey);
	}
	
	public void registProject(ProjectForm form) throws Exception {
		DbProject dbProject = new DbProject(form, form.getRepositoryUrl());
		projectMapper.insertProject(dbProject);
		jansibleGitter.cloneRepository(form, form.getRepositoryUrl());
		fileService.outputProjectData(dbProject);
	}

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
	}

	public DbProject getProject(ProjectKey projectKey){
		return projectMapper.selectProject(projectKey);
	}

	public List<DbEnvironment> getEnvironmentList(ProjectKey projectKey){
		return environmentMapper.selectEnvironmentList(projectKey);
	}

	public void registEnvironment(EnvironmentForm form) {
		DbEnvironment dbEnvironment = new DbEnvironment(form);
		environmentMapper.insertEnvironment(dbEnvironment);;
	}

	public void deleteEnvironment(EnvironmentKey environmentKey){
		environmentMapper.deleteEnvironment(environmentKey);
		serviceGroupMapper.deleteServiceGroupByEnvironment(environmentKey);
		serviceGroupMapper.deleteDbRoleRelationByEnvironment(environmentKey);
		serverMapper.deleteServerByEnvironment(environmentKey);
		variableMapper.deleteDbEnvironmentVariableByEnvironment(environmentKey);
		variableMapper.deleteDbServerVariableByEnvironment(environmentKey);
		variableMapper.deleteDbServiceGroupVariableByEnvironment(environmentKey);
		
		List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(environmentKey);
		for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
			jansibleFiler.deleteGroupVariableYaml(dbServiceGroup);
		}
	}

	public void deleteEnvironmentVariable(EnvironmentVariableKey environmentVariableKey){
		variableMapper.deleteDbEnvironmentVariable(environmentVariableKey);
	}

	public List<DbEnvironmentVariable> getDbEnvironmentVariableList(EnvironmentKey environmentKey){
		return variableMapper.selectDbEnvironmentVariableList(environmentKey);
	}

	public void registServiceGroup(ServiceGroupForm form) {
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form);
		serviceGroupMapper.insertServiceGroup(dbServiceGroup);
	}

	public void deleteServiceGroup(ServiceGroupKey serviceGroupKey){
		serviceGroupMapper.deleteServiceGroup(serviceGroupKey);
		serviceGroupMapper.deleteDbRoleRelationByServiceGroup(serviceGroupKey);
		serverMapper.deleteServerByServiceGroup(serviceGroupKey);
		variableMapper.deleteDbServiceGroupVariableByServiceGroup(serviceGroupKey);
		variableMapper.deleteDbServerVariableByServiceGroup(serviceGroupKey);
		jansibleFiler.deleteGroupVariableYaml(serviceGroupKey);
	}

	public void deleteServiceGroupVariable(ServiceGroupVariableKey serviceGroupVariableKey){
		variableMapper.deleteDbServiceGroupVariable(serviceGroupVariableKey);
	}

	public List<DbServiceGroup> getServiceGroupList(EnvironmentKey environmentKey){
		return serviceGroupMapper.selectServiceGroupList(environmentKey);
	}

	public void deleteRoleRelation(RoleRelationKey roleRelationKey){
		serviceGroupMapper.deleteDbRoleRelation(roleRelationKey);
	}

	public List<DbRoleRelation> getRoleRelationList(ServiceGroupKey serviceGroupKey){
		return serviceGroupMapper.selectDbRoleRelationList(serviceGroupKey);
	}

	public void deleteRoleVariable(RoleVariableKey roleVariableKey){
		variableMapper.deleteDbRoleVariable(roleVariableKey);
	}

	public void registServer(ServerForm form) {
		DbServer dbServer = new DbServer(form);
		serverMapper.insertServer(dbServer);
		
		fileService.outputHostsData(form);
	}

	public List<DbServiceGroupVariable> getDbServiceGroupVariableList(ServiceGroupKey serviceGroupKey){
		return variableMapper.selectDbServiceGroupVariableList(serviceGroupKey);
	}

	public List<DbServer> getServerList(ServiceGroupKey serviceGroupKey){
		return serverMapper.selectServerList(serviceGroupKey);
	}

	public List<String> getAllDbVariableNameList(ProjectKey projectKey){
		return variableMapper.selectAllDbVariableNameList(projectKey);
	}

	public List<DbServerVariable> getDbServerVariableList(ServerKey serverKey){
		return variableMapper.selectDbServerVariableList(serverKey);
	}

	public void deleteServerVariable(ServerVariableKey serverVariableKey){
		variableMapper.deleteDbServerVariable(serverVariableKey);
	}

	public void deleteServer(ServerKey serverKey){
		serverMapper.deleteServer(serverKey);
		variableMapper.deleteDbServerVariableByServer(serverKey);
		jansibleFiler.deleteHostVariableYaml(serverKey);
	}

	public void registRole(RoleForm form) {
		DbRole dbRole = new DbRole(form);
		roleMapper.insertRole(dbRole);
		fileService.outputRoleData(dbRole);
	}

	public List<DbRole> getRoleList(ProjectKey projectKey){
		return roleMapper.selectRoleList(projectKey);
	}
	
	public void deleteRole(RoleKey roleKey){
		roleMapper.deleteRole(roleKey);
		roleMapper.deleteDbFileByRole(roleKey);
		roleMapper.deleteDbTemplateByRole(roleKey);
		taskMapper.deleteTaskByRole(roleKey);
		taskMapper.deleteTaskDetailByRole(roleKey);
		variableMapper.deleteDbRoleVariableByRole(roleKey);
		jansibleFiler.deleteRoleDir(roleKey);
	}

	public List<DbRoleVariable> getDbRoleVariableList(RoleKey roleKey){
		return variableMapper.selectDbRoleVariableList(roleKey);
	}

	public void registFile(UploadForm form) {
		DbFile dbFile = createDbFile(form);
		roleMapper.insertDbFile(dbFile);
	}

	public void deleteFile(GeneralFileForm form){
		FileKey fileKey = new FileKey(form);
		fileKey.setFileName(form.getFileName());
		roleMapper.deleteDbFile(fileKey);
	}

	public void deleteTemplate(GeneralFileForm form){
		TemplateKey templateKey = new TemplateKey(form);
		templateKey.setTemplateName(form.getFileName());
		roleMapper.deleteDbTemplate(templateKey);
	}

	public void registRoleVariable(RoleVariableForm form) {
		DbRoleVariable dbRoleVariable = createDbRoleVariable(form);
		variableMapper.insertDbRoleVariable(dbRoleVariable);
		
		fileService.outputRoleVariableData(form);
	}

	public void registRoleRelationDetail(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = createDbRoleRelation(form);
		serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
		
		fileService.outputRoleRelationData(form);
	}

	public void registTemplate(UploadForm form) {
		DbTemplate dbTemplate = createDbTemplate(form);
		roleMapper.insertDbTemplate(dbTemplate);
	}

	public List<DbFile> getDbFileList(RoleKey roleKey){
		return roleMapper.selectDbFileList(roleKey);
	}

	public List<DbTemplate> getDbTemplateList(RoleKey roleKey){
		return roleMapper.selectDbTemplateList(roleKey);
	}

	public void deleteTask(TaskKey taskKey){
		taskMapper.deleteTask(taskKey);
		taskMapper.deleteTaskDetail(taskKey);
	}

	public void updateTask(TaskDetailForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.updateTask(dbTask);
		
		registTaskDetail(form);
		
		fileService.outputTaskData(form);
	}

	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.insertTask(dbTask);
	}

	public List<DbTask> getTaskList(RoleKey roleKey){
		return taskMapper.selectTaskList(roleKey);
	}
	
	public DbTask getTask(TaskKey taskKey){
		return taskMapper.selectTask(taskKey);
	}
	
	public void registServiceGroupVariable(ServiceGroupVariableForm form) {
		DbServiceGroupVariable dbServiceGroupVariable = createDbServiceGroupVariable(form);
		variableMapper.insertDbServiceGroupVariable(dbServiceGroupVariable);
		
		fileService.outputServiceGroupVariableData(form);
	}

	public void registServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = createDbServerVariable(form);
		variableMapper.insertDbServerVariable(dbServerVariable);
		
		fileService.outputServerVariableData(form);
	}

	public void registEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = createDbEnvironmentVariable(form);
		variableMapper.insertDbEnvironmentVariable(dbEnvironmentVariable);
		
		fileService.outputEnvironmentVariableData(form);
	}

	private void registTaskDetail(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
			taskMapper.insertTaskDetail(dbTaskDetail);
		}
	}

	public List<DbTaskDetail> getTaskDetailList(TaskKey taskKey){
		return taskMapper.selectTaskDetailList(taskKey);
	}
	
	private DbRoleRelation createDbRoleRelation(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = new DbRoleRelation(form);
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
		DbTask dbTask = new DbTask(form);
		dbTask.setModuleName(form.getModuleName());
		dbTask.setDescription(form.getDescription());
		dbTask.setSort(form.getSort());
		return dbTask;
	}

	private DbTask createDbTask(TaskDetailForm form) {
		DbTask dbTask = new DbTask(form);
		dbTask.setDescription(form.getDescription());
		return dbTask;
	}

	private DbFile createDbFile(UploadForm form) {
		DbFile dbFile = new DbFile(form);
		dbFile.setFileName(form.getFileName());
		return dbFile;
	}

	private DbTemplate createDbTemplate(UploadForm form) {
		DbTemplate dbTemplate = new DbTemplate(form);
		dbTemplate.setTemplateName(form.getFileName());
		return dbTemplate;
	}

	private DbEnvironmentVariable createDbEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = new DbEnvironmentVariable(form);
		dbEnvironmentVariable.setValue(form.getValue());
		return dbEnvironmentVariable;
	}

	private DbServerVariable createDbServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = new DbServerVariable(form);
		dbServerVariable.setValue(form.getValue());
		return dbServerVariable;
	}

	private DbServiceGroupVariable createDbServiceGroupVariable(ServiceGroupVariableForm form) {
		DbServiceGroupVariable dbServiceGroupVariable = new DbServiceGroupVariable(form);
		dbServiceGroupVariable.setValue(form.getValue());
		return dbServiceGroupVariable;
	}

	private DbRoleVariable createDbRoleVariable(RoleVariableForm form) {
		DbRoleVariable dbRoleVariable = new DbRoleVariable(form);
		dbRoleVariable.setValue(form.getValue());
		return dbRoleVariable;
	}
}
