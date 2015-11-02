package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import jansible.mapper.ProjectMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbServer;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.web.project.form.EnvironmentForm;
import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServiceGroupForm;
import jansible.web.project.form.TaskDetailForm;
import jansible.web.project.form.TaskForm;
import jansible.web.project.form.TaskParameter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;

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
	
	public DbTask getTask(String projectName, String roleName, Integer taskId){
		TaskKey taskKey = new TaskKey(projectName, roleName, taskId);
		return projectMapper.selectTask(taskKey);
	}
	
	public List<DbTaskDetail> getTaskDetailList(String projectName, String roleName, Integer taskId){
		TaskKey taskKey = new TaskKey(projectName, roleName, taskId);
		return projectMapper.selectTaskDetailList(taskKey);
	}

	public void registProject(ProjectForm form) {
		DbProject dbProject = new DbProject(form.getProjectName());
		projectMapper.insertProject(dbProject);
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
	}
	
	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		projectMapper.insertTask(dbTask);
	}
	
	public void registTaskDetail(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
			projectMapper.insertTaskDetail(dbTaskDetail);
		}
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

	private DbServer createDbServer(ServerForm form){
		return new DbServer(form.getProjectName(), form.getEnvironmentName(), form.getGroupName(), form.getServerName());
	}
	
	private DbRole createDbRole(RoleForm form){	
		return new DbRole(form.getProjectName(), form.getRoleName());
	}

	public List<TaskParameter> getTaskParameterList(String projectName, String roleName, String moduleName) {
		// TODO Auto-generated method stub
		return null;
	}
}
