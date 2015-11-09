package jansible.mapper;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerVariableKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.model.database.DbEnvironmentVariable;
import jansible.model.database.DbRoleVariable;
import jansible.model.database.DbServerVariable;
import jansible.model.database.DbServiceGroupVariable;

import java.util.List;

public interface VariableMapper {	
	List<String> selectAllDbVariableNameList(ProjectKey projectKey);
	
	void insertDbEnvironmentVariable(DbEnvironmentVariable dbEnvironmentVariable);
	void insertDbServiceGroupVariable(DbServiceGroupVariable dbServiceGroupVariable);
	void insertDbServerVariable(DbServerVariable dbServerVariable);
	void insertDbRoleVariable(DbRoleVariable dbRoleVariable);
	
	List<DbEnvironmentVariable> selectDbEnvironmentVariableList(EnvironmentKey environmentKey);
	List<DbServiceGroupVariable> selectDbServiceGroupVariableList(ServiceGroupKey serviceGroupKey);
	List<DbServerVariable> selectDbServerVariableList(ServerKey serverKey);
	List<DbRoleVariable> selectDbRoleVariableList(RoleKey roleKey);
	
	void deleteDbEnvironmentVariable(EnvironmentVariableKey environmentVariableKey);
	void deleteDbEnvironmentVariableByEnvironment(EnvironmentKey environmentKey);
	
	void deleteDbServiceGroupVariable(ServiceGroupVariableKey serviceGroupVariableKey);
	void deleteDbServiceGroupVariableByServiceGroup(ServiceGroupKey serviceGroupKey);
	void deleteDbServiceGroupVariableByEnvironment(EnvironmentKey environmentKey);
	
	void deleteDbServerVariable(ServerVariableKey serverVariableKey);
	void deleteDbServerVariableByServer(ServerKey serverKey);
	void deleteDbServerVariableByServiceGroup(ServiceGroupKey serviceGroupKey);
	void deleteDbServerVariableByEnvironment(EnvironmentKey environmentKey);
	
	void deleteDbRoleVariable(RoleVariableKey roleVariableKey);
	void deleteDbRoleVariableByRole(RoleKey roleKey);
}
