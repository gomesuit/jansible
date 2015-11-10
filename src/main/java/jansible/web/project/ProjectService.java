package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import jansible.file.HostGroup;
import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.git.JansibleGitter;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.ProjectMapper;
import jansible.mapper.RoleMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.TaskMapper;
import jansible.mapper.VariableMapper;
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
import jansible.model.database.InterfaceDbVariable;
import jansible.model.yamldump.StartYaml;
import jansible.model.yamldump.YamlVariable;
import jansible.util.YamlDumper;
import jansible.web.project.form.EnvironmentForm;
import jansible.web.project.form.EnvironmentVariableForm;
import jansible.web.project.form.GeneralFileForm;
import jansible.web.project.form.GitForm;
import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.RoleRelationForm;
import jansible.web.project.form.RoleVariableForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServerVariableForm;
import jansible.web.project.form.ServiceGroupForm;
import jansible.web.project.form.ServiceGroupVariableForm;
import jansible.web.project.form.TaskDetailForm;
import jansible.web.project.form.TaskForm;
import jansible.web.project.form.TaskParameter;
import jansible.web.project.form.UploadForm;

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
	private JansibleFiler jansibleFiler;
	@Autowired
	private YamlDumper yamlDumper;
	@Autowired
	private JansibleGitter jansibleGitter;
	@Autowired
	private JansibleHostsDumper jansibleHostsDumper;

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
	}

	public List<DbEnvironment> getEnvironmentList(ProjectKey projectKey){
		return environmentMapper.selectEnvironmentList(projectKey);
	}

	public List<DbServiceGroup> getServiceGroupList(EnvironmentKey environmentKey){
		return serviceGroupMapper.selectServiceGroupList(environmentKey);
	}

	public List<DbServer> getServerList(ServiceGroupKey serviceGroupKey){
		return serverMapper.selectServerList(serviceGroupKey);
	}

	public List<DbRole> getRoleList(ProjectKey projectKey){
		return roleMapper.selectRoleList(projectKey);
	}
	
	public List<DbTask> getTaskList(RoleKey roleKey){
		return taskMapper.selectTaskList(roleKey);
	}
	
	public List<DbFile> getDbFileList(RoleKey roleKey){
		return roleMapper.selectDbFileList(roleKey);
	}
	
	public List<DbTemplate> getDbTemplateList(RoleKey roleKey){
		return roleMapper.selectDbTemplateList(roleKey);
	}
	
	public List<DbRoleVariable> getDbRoleVariableList(RoleKey roleKey){
		return variableMapper.selectDbRoleVariableList(roleKey);
	}
	
	public List<DbServiceGroupVariable> getDbServiceGroupVariableList(ServiceGroupKey serviceGroupKey){
		return variableMapper.selectDbServiceGroupVariableList(serviceGroupKey);
	}
	
	public List<DbServerVariable> getDbServerVariableList(ServerKey serverKey){
		return variableMapper.selectDbServerVariableList(serverKey);
	}
	
	public List<DbEnvironmentVariable> getDbEnvironmentVariableList(EnvironmentKey environmentKey){
		return variableMapper.selectDbEnvironmentVariableList(environmentKey);
	}
	
	public List<String> getAllDbVariableNameList(ProjectKey projectKey){
		return variableMapper.selectAllDbVariableNameList(projectKey);
	}
	
	public DbTask getTask(TaskKey taskKey){
		return taskMapper.selectTask(taskKey);
	}
	
	public List<DbTaskDetail> getTaskDetailList(TaskKey taskKey){
		return taskMapper.selectTaskDetailList(taskKey);
	}
	
	public void deleteTask(TaskKey taskKey){
		taskMapper.deleteTask(taskKey);
		taskMapper.deleteTaskDetail(taskKey);
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
	
	public void deleteServer(ServerKey serverKey){
		serverMapper.deleteServer(serverKey);
		variableMapper.deleteDbServerVariableByServer(serverKey);
		jansibleFiler.deleteHostVariableYaml(serverKey);
	}
	
	public void deleteRoleRelation(RoleRelationKey roleRelationKey){
		serviceGroupMapper.deleteDbRoleRelation(roleRelationKey);
	}
	
	public void deleteServiceGroup(ServiceGroupKey serviceGroupKey){
		serviceGroupMapper.deleteServiceGroup(serviceGroupKey);
		serviceGroupMapper.deleteDbRoleRelationByServiceGroup(serviceGroupKey);
		serverMapper.deleteServerByServiceGroup(serviceGroupKey);
		variableMapper.deleteDbServiceGroupVariableByServiceGroup(serviceGroupKey);
		variableMapper.deleteDbServerVariableByServiceGroup(serviceGroupKey);
		jansibleFiler.deleteGroupVariableYaml(serviceGroupKey);
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
	
	public void deleteServiceGroupVariable(ServiceGroupVariableKey serviceGroupVariableKey){
		variableMapper.deleteDbServiceGroupVariable(serviceGroupVariableKey);
	}
	
	public void deleteServerVariable(ServerVariableKey serverVariableKey){
		variableMapper.deleteDbServerVariable(serverVariableKey);
	}
	
	public void deleteRoleVariable(RoleVariableKey roleVariableKey){
		variableMapper.deleteDbRoleVariable(roleVariableKey);
	}
	
	public List<DbRoleRelation> getRoleRelationList(ServiceGroupKey serviceGroupKey){
		return serviceGroupMapper.selectDbRoleRelationList(serviceGroupKey);
	}


	public void registProject(ProjectForm form) {
		DbProject dbProject = new DbProject(form.getProjectName(), form.getRepositoryUrl());
		projectMapper.insertProject(dbProject);
		jansibleGitter.cloneRepository(form, form.getRepositoryUrl());
		//jansibleFiler.mkProjectDir(dbProject);
		jansibleFiler.mkHostVariableDir(dbProject);
		jansibleFiler.mkGroupVariableDir(dbProject);
	}
	
	public void registEnvironment(EnvironmentForm form) {
		DbEnvironment dbEnvironment = new DbEnvironment(form);
		environmentMapper.insertEnvironment(dbEnvironment);;
	}
	
	public void registServiceGroup(ServiceGroupForm form) {
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form);
		serviceGroupMapper.insertServiceGroup(dbServiceGroup);
	}
	
	public void registServer(ServerForm form) {
		DbServer dbServer = new DbServer(form);
		serverMapper.insertServer(dbServer);
		
		List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(form);
		List<HostGroup> hostGroupList = new ArrayList<>();
		for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
			HostGroup hostGroup = new HostGroup();
			hostGroup.setGroupName(jansibleFiler.getGroupName(dbServiceGroup));
			List<DbServer> dbServerList = serverMapper.selectServerList(dbServiceGroup);
			for(DbServer server : dbServerList){
				hostGroup.addHost(server.getServerName());
			}
			hostGroupList.add(hostGroup);
		}
		String hostsFileContent = jansibleHostsDumper.getString(hostGroupList);
		jansibleFiler.writeHostsFile(form, hostsFileContent);
	}
	
	public void registRole(RoleForm form) {
		DbRole dbRole = new DbRole(form);;
		roleMapper.insertRole(dbRole);
		jansibleFiler.mkRoleDir(dbRole);
		jansibleFiler.mkRoleTaskDir(dbRole);
		jansibleFiler.mkRoleTemplateDir(dbRole);
		jansibleFiler.mkRoleFileDir(dbRole);
		jansibleFiler.mkRoleVariableDir(dbRole);
		jansibleFiler.writeRoleYaml(dbRole);
	}
	
	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.insertTask(dbTask);
	}
	
	public void updateTask(TaskDetailForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.updateTask(dbTask);
	}
	
	public void registTaskDetail(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
			taskMapper.insertTaskDetail(dbTaskDetail);
		}
	}
	
	public void registRoleRelationDetail(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = createDbRoleRelation(form);
		serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
		
		StartYaml startYaml = new StartYaml();
		startYaml.setHosts(jansibleFiler.getGroupName(form));
		
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(form);
		for(DbRoleRelation roleRelation : dbRoleRelationList){
			startYaml.addRole(roleRelation.getRoleName());
		}
		String yamlContent = yamlDumper.dumpStartYaml(startYaml);
		jansibleFiler.writeStartYaml(form, yamlContent);
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
	
	public void registFile(UploadForm form) {
		DbFile dbFile = createDbFile(form);
		roleMapper.insertDbFile(dbFile);
	}

	private DbFile createDbFile(UploadForm form) {
		DbFile dbFile = new DbFile(form);
		dbFile.setFileName(form.getFileName());
		return dbFile;
	}
	
	public void registTemplate(UploadForm form) {
		DbTemplate dbTemplate = createDbTemplate(form);
		roleMapper.insertDbTemplate(dbTemplate);
	}

	private DbTemplate createDbTemplate(UploadForm form) {
		DbTemplate dbTemplate = new DbTemplate(form);
		dbTemplate.setTemplateName(form.getFileName());
		return dbTemplate;
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
		
		List<DbRoleVariable> dbRoleVariableList = variableMapper.selectDbRoleVariableList(form);
		List<YamlVariable> yamlVariableList = createYamlVariableList(dbRoleVariableList);
		String yamlContent = yamlDumper.dumpVariable(yamlVariableList);
		jansibleFiler.writeRoleVariableYaml(form, yamlContent);
	}
	
	private <T extends InterfaceDbVariable> List<YamlVariable> createYamlVariableList(List<T> dbVariableList){
		List<YamlVariable> yamlVariableList = new ArrayList<>();
		for(InterfaceDbVariable dbVariable : dbVariableList){
			yamlVariableList.add(new YamlVariable(dbVariable.getVariableName(), dbVariable.getValue()));
		}
		return yamlVariableList;
	}
	
	public void registServiceGroupVariable(ServiceGroupVariableForm form) {
		DbServiceGroupVariable dbServiceGroupVariable = createDbServiceGroupVariable(form);
		variableMapper.insertDbServiceGroupVariable(dbServiceGroupVariable);
		
		writeServiceGroupVariableYaml(form);
	}
	
	private void writeServiceGroupVariableYaml(ServiceGroupKey serviceGroupKey){
		List<DbEnvironmentVariable> dbEnvironmentVariableList = variableMapper.selectDbEnvironmentVariableList(serviceGroupKey);
		List<YamlVariable> envYamlVariableList = createYamlVariableList(dbEnvironmentVariableList);
		
		List<DbServiceGroupVariable> dbServiceGroupVariableList = variableMapper.selectDbServiceGroupVariableList(serviceGroupKey);
		List<YamlVariable> groupVamlVariableList = createYamlVariableList(dbServiceGroupVariableList);
		
		envYamlVariableList.addAll(groupVamlVariableList);
		
		String yamlContent = yamlDumper.dumpVariable(envYamlVariableList);
		jansibleFiler.writeGroupVariableYaml(serviceGroupKey, yamlContent);
	}
	
	public void registServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = createDbServerVariable(form);
		variableMapper.insertDbServerVariable(dbServerVariable);
		
		List<DbServerVariable> dbServerVariableList = variableMapper.selectDbServerVariableList(form);
		List<YamlVariable> yamlVariableList = createYamlVariableList(dbServerVariableList);
		String yamlContent = yamlDumper.dumpVariable(yamlVariableList);
		jansibleFiler.writeHostVariableYaml(form, yamlContent);
	}
	
	public void registEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = createDbEnvironmentVariable(form);
		variableMapper.insertDbEnvironmentVariable(dbEnvironmentVariable);
		
		writeEnvironmentVariableYaml(form);
	}
	
	private void writeEnvironmentVariableYaml(EnvironmentKey environmentKey){
		List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(environmentKey);
		
		for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
			writeServiceGroupVariableYaml(dbServiceGroup);
		}
	}

	private DbEnvironmentVariable createDbEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = new DbEnvironmentVariable();
		dbEnvironmentVariable.setProjectName(form.getProjectName());
		dbEnvironmentVariable.setEnvironmentName(form.getEnvironmentName());
		dbEnvironmentVariable.setVariableName(form.getVariableName());
		dbEnvironmentVariable.setValue(form.getValue());
		return dbEnvironmentVariable;
	}

	private DbServerVariable createDbServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = new DbServerVariable();
		dbServerVariable.setProjectName(form.getProjectName());
		dbServerVariable.setEnvironmentName(form.getEnvironmentName());
		dbServerVariable.setGroupName(form.getGroupName());
		dbServerVariable.setServerName(form.getServerName());
		dbServerVariable.setVariableName(form.getVariableName());
		dbServerVariable.setValue(form.getValue());
		return dbServerVariable;
	}

	private DbServiceGroupVariable createDbServiceGroupVariable(ServiceGroupVariableForm form) {
		DbServiceGroupVariable dbServiceGroupVariable = new DbServiceGroupVariable();
		dbServiceGroupVariable.setProjectName(form.getProjectName());
		dbServiceGroupVariable.setEnvironmentName(form.getEnvironmentName());
		dbServiceGroupVariable.setGroupName(form.getGroupName());
		dbServiceGroupVariable.setVariableName(form.getVariableName());
		dbServiceGroupVariable.setValue(form.getValue());
		return dbServiceGroupVariable;
	}

	private DbRoleVariable createDbRoleVariable(RoleVariableForm form) {
		DbRoleVariable dbRoleVariable = new DbRoleVariable();
		dbRoleVariable.setProjectName(form.getProjectName());
		dbRoleVariable.setRoleName(form.getRoleName());
		dbRoleVariable.setVariableName(form.getVariableName());
		dbRoleVariable.setValue(form.getValue());
		return dbRoleVariable;
	}

	public void commitGit(GitForm form) {
		jansibleGitter.commitAndPush(form, form.getUserName(), form.getPassword(), form.getComment());
	}
}
