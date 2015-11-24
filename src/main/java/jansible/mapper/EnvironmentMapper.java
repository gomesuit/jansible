package jansible.mapper;

import java.util.List;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbEnvironment;

public interface EnvironmentMapper {
	void insertEnvironment(DbEnvironment dbEnvironment);
	List<DbEnvironment> selectEnvironmentList(ProjectKey projectKey);
	void deleteEnvironment(EnvironmentKey environmentKey);
	void deleteEnvironmentByProject(ProjectKey projectKey);
}
