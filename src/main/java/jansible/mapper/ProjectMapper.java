package jansible.mapper;

import jansible.model.common.ProjectKey;
import jansible.model.database.DbProject;

import java.util.List;

public interface ProjectMapper {
	void insertProject(DbProject dbProject);
	List<DbProject> selectProjectList();
	DbProject selectProject(ProjectKey projectKey);
	
	void updateJenkinsInfo(DbProject dbProject);
}
