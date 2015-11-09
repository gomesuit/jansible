package jansible.mapper;

import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbServer;

import java.util.List;

public interface ServerMapper {
	void insertServer(DbServer dbServer);
	List<DbServer> selectServerList(ServiceGroupKey serviceGroupKey);
}
