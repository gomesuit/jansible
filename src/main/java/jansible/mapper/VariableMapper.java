package jansible.mapper;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;
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
}
