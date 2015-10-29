package jansible.mapper;

import java.util.List;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbServer;
import jansible.model.database.DbServiceGroup;

public interface ProjectMapper {
	void insertProject(DbProject dbProject);
	List<DbProject> selectProjectList();

	void insertEnvironment(DbEnvironment dbEnvironment);
	List<DbEnvironment> selectEnvironmentList(ProjectKey projectKey);
	
	void insertServiceGroup(DbServiceGroup dbServiceGroup);
	List<DbServiceGroup> selectServiceGroupList(EnvironmentKey environmentKey);
	
	void insertServer(DbServer dbServer);
	List<DbServer> selectServerList(ServiceGroupKey serviceGroupKey);

	void insertRole(DbRole dbRole);
	List<DbRole> selectRoleList(ProjectKey projectKey);
}
