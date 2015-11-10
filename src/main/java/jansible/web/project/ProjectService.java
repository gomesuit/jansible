package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import jansible.file.HostGroup;
import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.git.JansibleGitter;
import jansible.jenkins.JenkinsBuilder;
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
import jansible.model.jenkins.JenkinsParameter;
import jansible.model.yamldump.StartYaml;
import jansible.model.yamldump.YamlModule;
import jansible.model.yamldump.YamlParameter;
import jansible.model.yamldump.YamlParameters;
import jansible.model.yamldump.YamlVariable;
import jansible.util.YamlDumper;
import jansible.web.project.form.BuildForm;
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

import org.apache.commons.lang.StringUtils;
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
	@Autowired
	private JenkinsBuilder jenkinsBuilder;
	
	public void build(BuildForm form){
		DbProject project = getProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getGroupName(form));
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsBuilder.build("http://192.168.33.11:8080/job/test2/build?delay=0sec", jenkinsParameter);
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
		outputProjectData(dbProject);
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
		
		outputHostsData(form);
	}
	
	public void registRole(RoleForm form) {
		DbRole dbRole = new DbRole(form);
		roleMapper.insertRole(dbRole);
		outputRoleData(dbRole);
	}
	
	private void outputProjectData(ProjectKey projectKey){
		jansibleFiler.mkHostVariableDir(projectKey);
		jansibleFiler.mkGroupVariableDir(projectKey);
	}
	
	private void outputRoleData(RoleKey roleKey){
		jansibleFiler.mkRoleDir(roleKey);
		jansibleFiler.mkRoleTaskDir(roleKey);
		jansibleFiler.mkRoleTemplateDir(roleKey);
		jansibleFiler.mkRoleFileDir(roleKey);
		jansibleFiler.mkRoleVariableDir(roleKey);
		jansibleFiler.writeRoleYaml(roleKey);
	}
	
	private void outputRoleVariableData(RoleKey roleKey){
		List<DbRoleVariable> dbRoleVariableList = variableMapper.selectDbRoleVariableList(roleKey);
		List<YamlVariable> yamlVariableList = createYamlVariableList(dbRoleVariableList);
		String yamlContent = yamlDumper.dumpVariable(yamlVariableList);
		jansibleFiler.writeRoleVariableYaml(roleKey, yamlContent);
	}
	
	private void outputServiceGroupVariableData(ServiceGroupKey serviceGroupKey){
		List<DbEnvironmentVariable> dbEnvironmentVariableList = variableMapper.selectDbEnvironmentVariableList(serviceGroupKey);
		List<YamlVariable> envYamlVariableList = createYamlVariableList(dbEnvironmentVariableList);
		
		List<DbServiceGroupVariable> dbServiceGroupVariableList = variableMapper.selectDbServiceGroupVariableList(serviceGroupKey);
		List<YamlVariable> groupVamlVariableList = createYamlVariableList(dbServiceGroupVariableList);
		
		envYamlVariableList.addAll(groupVamlVariableList);
		
		if(!envYamlVariableList.isEmpty()){
			String yamlContent = yamlDumper.dumpVariable(envYamlVariableList);
			jansibleFiler.writeGroupVariableYaml(serviceGroupKey, yamlContent);
		}
	}

	private void outputRoleRelationData(ServiceGroupKey serviceGroupKey){
		StartYaml startYaml = new StartYaml();
		startYaml.setHosts(jansibleFiler.getGroupName(serviceGroupKey));
		
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(serviceGroupKey);
		for(DbRoleRelation roleRelation : dbRoleRelationList){
			startYaml.addRole(roleRelation.getRoleName());
		}
		String yamlContent = yamlDumper.dumpStartYaml(startYaml);
		jansibleFiler.writeStartYaml(serviceGroupKey, yamlContent);
	}
	
	private void outputTaskData(TaskKey taskKey){
    	List<DbTask> dbTaskList = getTaskList(taskKey);
    	List<YamlModule> modules = createYamlModuleList(dbTaskList);
    	jansibleFiler.writeRoleYaml(taskKey, yamlDumper.dump(modules));
	}

	private void outputServerVariableData(ServerKey serverKey){
		List<DbServerVariable> dbServerVariableList = variableMapper.selectDbServerVariableList(serverKey);
		List<YamlVariable> yamlVariableList = createYamlVariableList(dbServerVariableList);
		
		if(!yamlVariableList.isEmpty()){
			String yamlContent = yamlDumper.dumpVariable(yamlVariableList);
			jansibleFiler.writeHostVariableYaml(serverKey, yamlContent);
		}
	}
	
	private void outputHostsData(ProjectKey projectKey){
		List<HostGroup> hostGroupList = createHostGroupList(projectKey);
		
		if(!hostGroupList.isEmpty()){
			String hostsFileContent = jansibleHostsDumper.getString(hostGroupList);
			jansibleFiler.writeHostsFile(projectKey, hostsFileContent);
		}
	}
	
	private List<HostGroup> createHostGroupList(ProjectKey projectKey){
		List<HostGroup> hostGroupList = new ArrayList<>();
		
		List<DbEnvironment> dbEnvironmentList = environmentMapper.selectEnvironmentList(projectKey);
		for(DbEnvironment dbEnvironment : dbEnvironmentList){
			List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(dbEnvironment);
			for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
				HostGroup hostGroup = createHostGroup(dbServiceGroup);
				hostGroupList.add(hostGroup);
			}
		}
		return hostGroupList;
	}
	
	private HostGroup createHostGroup(ServiceGroupKey serviceGroupKey){
		HostGroup hostGroup = new HostGroup();
		hostGroup.setGroupName(jansibleFiler.getGroupName(serviceGroupKey));
		List<DbServer> dbServerList = serverMapper.selectServerList(serviceGroupKey);
		for(DbServer server : dbServerList){
			hostGroup.addHost(server.getServerName());
		}
		return hostGroup;
	}
	
	private void outputEnvironmentVariableData(EnvironmentKey environmentKey){
		List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(environmentKey);
		
		for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
			outputServiceGroupVariableData(dbServiceGroup);
		}
	}

	public void reOutputAllData(ProjectKey projectKey){
		jansibleFiler.deleteAllStartYamlfile(projectKey);
		jansibleFiler.deleteGroupVariableDir(projectKey);
		jansibleFiler.deleteHostVariableDir(projectKey);
		
		outputProjectData(projectKey);
		outputHostsData(projectKey);
		List<DbEnvironment> dbEnvironmentList = environmentMapper.selectEnvironmentList(projectKey);
		for(DbEnvironment dbEnvironment : dbEnvironmentList){
			List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(dbEnvironment);
			for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
				outputServiceGroupVariableData(dbServiceGroup);
				outputRoleRelationData(dbServiceGroup);
				List<DbServer> dbServerList = serverMapper.selectServerList(dbServiceGroup);
				for(DbServer dbServer : dbServerList){
					outputServerVariableData(dbServer);
				}
			}
		}
	}

	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.insertTask(dbTask);
	}
	
	public void updateTask(TaskDetailForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.updateTask(dbTask);
		
		registTaskDetail(form);
		
		outputTaskData(form);
	}
    
    public List<YamlModule> createYamlModuleList(List<DbTask> dbTaskList){
    	List<YamlModule> modules = new ArrayList<>();
    	for(DbTask dbTask : dbTaskList){
    		List<DbTaskDetail> dbTaskDetailList = getTaskDetailList(dbTask);
    		YamlModule yamlModule = new YamlModule(dbTask.getModuleName(), createParameters(dbTaskDetailList));
    		yamlModule.setDescription(dbTask.getDescription());
    		modules.add(yamlModule);
    	}
    	return modules;
    }
    
    public YamlParameters createParameters(List<DbTaskDetail> dbTaskDetailList) {
    	YamlParameters yamlParameters = new YamlParameters();
    	for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
    		if(StringUtils.isBlank(dbTaskDetail.getParameterValue())){
    			continue;
    		}
    		YamlParameter YamlParameter = new YamlParameter(dbTaskDetail.getParameterName(), dbTaskDetail.getParameterValue());
    		yamlParameters.addParameter(YamlParameter);
    	}
		return yamlParameters;
	}
	
	private void registTaskDetail(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
			taskMapper.insertTaskDetail(dbTaskDetail);
		}
	}
	
	public void registRoleRelationDetail(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = createDbRoleRelation(form);
		serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
		
		outputRoleRelationData(form);
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
		
		outputRoleVariableData(form);
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
		
		outputServiceGroupVariableData(form);
	}
	
	public void registServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = createDbServerVariable(form);
		variableMapper.insertDbServerVariable(dbServerVariable);
		
		outputServerVariableData(form);
	}
	
	public void registEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = createDbEnvironmentVariable(form);
		variableMapper.insertDbEnvironmentVariable(dbEnvironmentVariable);
		
		outputEnvironmentVariableData(form);
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

	public void commitGit(GitForm form) {
		reOutputAllData(form);
		jansibleGitter.commitAndPush(form, form.getUserName(), form.getPassword(), form.getComment());
	}
}
