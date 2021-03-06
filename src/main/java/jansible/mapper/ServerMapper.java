package jansible.mapper;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerParameterKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbServer;
import jansible.model.database.DbServerParameter;

import java.util.List;

public interface ServerMapper {
	void insertServer(DbServer dbServer);
	List<DbServer> selectServerList(ProjectKey projectKey);
	List<DbServer> selectServerListByEnvironment(EnvironmentKey environmentKey);
	List<DbServer> selectServerListByServiceGroup(ServiceGroupKey serviceGroupKey);
	void deleteServer(ServerKey serverKey);
	void deleteServerByProject(ProjectKey projectKey);

	void insertServerParameter(DbServerParameter abServerParameter);
	List<DbServerParameter> selectServerParameterList(ServerKey serverKey);
	void deleteServerParameter(ServerParameterKey serverParameterKey);
	void deleteServerParameterByServer(ServerKey serverKey);
	void deleteServerParameterByProject(ProjectKey projectKey);
}
