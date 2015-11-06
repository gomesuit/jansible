package jansible.mapper;

import java.util.List;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;
import jansible.model.common.FileKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.ServerVariableKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.model.common.TaskKey;
import jansible.model.common.TemplateKey;
import jansible.model.common.VariableKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbEnvironmentVariable;
import jansible.model.database.DbFile;
import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbRoleVariable;
import jansible.model.database.DbServer;
import jansible.model.database.DbServerVariable;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbServiceGroupVariable;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.database.DbTemplate;
import jansible.model.database.DbVariable;

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
	void updateTask(DbTask dbTask);

	void insertTaskDetail(DbTaskDetail dbTaskDetail);
	List<DbTaskDetail> selectTaskDetailList(TaskKey taskKey);

	void insertDbRoleRelation(DbRoleRelation dbRoleRelation);
	List<DbRoleRelation> selectDbRoleRelationList(ServiceGroupKey serviceGroupKey);
	
	void insertDbFile(DbFile dbFile);
	List<DbFile> selectDbFileList(RoleKey roleKey);
	void deleteDbFile(FileKey fileKey);
	
	void insertDbTemplate(DbTemplate dbTemplate);
	List<DbTemplate> selectDbTemplateList(RoleKey roleKey);
	void deleteDbTemplate(TemplateKey templateKey);
	
	void insertDbVariable(DbVariable dbVariable);
	List<DbVariable> selectDbVariableList(VariableKey variableKey);
	List<String> selectAllDbVariableNameList(ProjectKey projectKey);
	
	void insertDbEnvironmentVariable(DbEnvironmentVariable dbEnvironmentVariable);
	void insertDbServiceGroupVariable(DbServiceGroupVariable dbServiceGroupVariable);
	void insertDbServerVariable(DbServerVariable dbServerVariable);
	void insertDbRoleVariable(DbRoleVariable dbRoleVariable);
	List<DbEnvironmentVariable> selectDbEnvironmentVariableList(EnvironmentVariableKey environmentVariableKey);
	List<DbServiceGroupVariable> selectDbServiceGroupVariableList(ServiceGroupVariableKey serviceGroupVariableKey);
	List<DbServerVariable> selectDbServerVariableList(ServerVariableKey serverVariableKey);
	List<DbRoleVariable> selectDbRoleVariableList(RoleVariableKey roleVariableKey);
}
