package jansible.web.project;

import java.util.List;

import jansible.file.JansibleFiler;
import jansible.mapper.ApplyHistoryMapper;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.ProjectMapper;
import jansible.mapper.RoleMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.TaskMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.database.DbApplyHistory;
import jansible.model.database.DbFile;
import jansible.model.database.DbProject;
import jansible.model.database.DbTemplate;
import jansible.web.project.project.JenkinsInfoForm;
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
	private GitService gitService;
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
		gitService.cloneRepository(form);
		fileService.outputProjectData(dbProject);
	}

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
	}

	public DbProject getProject(ProjectKey projectKey){
		return projectMapper.selectProject(projectKey);
	}

	public List<DbFile> getDbFileList(RoleKey roleKey){
		return roleMapper.selectDbFileList(roleKey);
	}

	public List<DbTemplate> getDbTemplateList(RoleKey roleKey){
		return roleMapper.selectDbTemplateList(roleKey);
	}
}
