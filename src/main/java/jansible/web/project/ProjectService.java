package jansible.web.project;

import java.util.List;

import jansible.mapper.ProjectMapper;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbProject;
import jansible.web.project.project.JenkinsInfoForm;
import jansible.web.project.top.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
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
}
