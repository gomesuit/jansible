package jansible.web.project;

import java.util.List;

import jansible.mapper.ProjectMapper;
import jansible.model.database.DbProject;
import jansible.web.project.form.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	public void insertProject(DbProject dbProject){
		projectMapper.insertProject(dbProject);
	}

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
	}

	public void registProject(ProjectForm form) {
		DbProject dbProject = new DbProject();
		dbProject.setProjectName(form.getProjectName());
		insertProject(dbProject);
	}
}
