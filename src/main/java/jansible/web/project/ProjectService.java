package jansible.web.project;

import java.util.List;

import jansible.mapper.ProjectMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbServer;
import jansible.model.database.DbServiceGroup;
import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServiceGroupForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
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

	public void registProject(ProjectForm form) {
		DbProject dbProject = new DbProject(form.getProjectName());
		projectMapper.insertProject(dbProject);
	}
	
	public void registServiceGroup(ServiceGroupForm form) {
		DbServiceGroup dbServiceGroup = createDbProject(form);
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

	private DbServiceGroup createDbProject(ServiceGroupForm form){
		return new DbServiceGroup(form.getProjectName(), form.getEnvironmentName(), form.getGroupName());
	}
	
	private DbServer createDbServer(ServerForm form){
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form.getProjectName(), form.getEnvironmentName(), form.getGroupName());
		return new DbServer(dbServiceGroup, form.getServerName());
	}
	
	private DbRole createDbRole(RoleForm form){	
		DbProject dbProject = new DbProject(form.getProjectName());
		return new DbRole(dbProject, form.getRoleName());
	}
}
