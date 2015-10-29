package jansible.web.project;

import java.util.List;

import jansible.mapper.ProjectMapper;
import jansible.model.database.DbProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	public void insertProject(DbProject dbProject){
		projectMapper.insertProject(dbProject);
	}

	public List<DbProject> selectProjectList(){
		return projectMapper.selectProjectList();
	}
}
