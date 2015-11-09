package jansible.mapper;

import jansible.model.database.DbProject;

import java.util.List;

public interface ProjectMapper {
	void insertProject(DbProject dbProject);
	List<DbProject> selectProjectList();
}
