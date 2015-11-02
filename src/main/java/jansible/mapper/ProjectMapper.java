package jansible.mapper;

import java.util.List;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbServer;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;

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
	
	void insertTask(DbTask dbTask);
	List<DbTask> selectTaskList(RoleKey roleKey);
	DbTask selectTask(TaskKey taskKey);

	void insertTaskDetail(DbTaskDetail dbTaskDetail);
	List<DbTaskDetail> selectTaskDetailList(TaskKey taskKey);
}
