package jansible.mapper;

import java.util.List;

import jansible.model.database.DbProject;

public interface ProjectMapper {
	void insertProject(DbProject dbProject);
	List<DbProject> selectProjectList();
}
