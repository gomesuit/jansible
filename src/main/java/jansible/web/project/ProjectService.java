package jansible.web.project;

import java.util.List;

import jansible.mapper.ProjectMapper;
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

	public List<DbServiceGroup> getServiceGroupList(String projectName){
		DbProject dbProject = new DbProject(projectName);
		return projectMapper.selectServiceGroupList(dbProject);
	}

	public List<DbServer> getServerList(String projectName, String groupName){
		DbServiceGroup dbServiceGroup = new DbServiceGroup(projectName, groupName);
		return projectMapper.selectServerList(dbServiceGroup);
	}

	public List<DbRole> getRoleList(String projectName){
		DbProject dbProject = new DbProject(projectName);
		return projectMapper.selectRoleList(dbProject);
	}

	public void registProject(ProjectForm form) {
		DbProject dbProject = createDbProject(form);
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

	private DbProject createDbProject(ProjectForm form){
		return new DbProject(form.getProjectName());
	}

	private DbServiceGroup createDbProject(ServiceGroupForm form){
		return new DbServiceGroup(form.getProjectName(), form.getGroupName());
	}
	
	private DbServer createDbServer(ServerForm form){
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form.getProjectName(), form.getGroupName());
		return new DbServer(dbServiceGroup, form.getServerName());
	}
	
	private DbRole createDbRole(RoleForm form){	
		DbProject dbProject = createDbProject(form.getProjectForm());
		return new DbRole(dbProject, form.getRoleName());
	}
}
