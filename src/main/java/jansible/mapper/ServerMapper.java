package jansible.mapper;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbServer;

import java.util.List;

public interface ServerMapper {
	void insertServer(DbServer dbServer);
	List<DbServer> selectServerList(ServiceGroupKey serviceGroupKey);
	void deleteServer(ServerKey serverKey);
	void deleteServerByServiceGroup(ServiceGroupKey serviceGroupKey);
	void deleteServerByEnvironment(EnvironmentKey environmentKey);
}
