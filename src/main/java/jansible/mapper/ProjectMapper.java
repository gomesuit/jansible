package jansible.mapper;

import java.util.List;

import jansible.model.database.DbProject;
import jansible.model.database.DbRole;
import jansible.model.database.DbServer;
import jansible.model.database.DbServiceGroup;

public interface ProjectMapper {
	void insertProject(DbProject dbProject);
	List<DbProject> selectProjectList();
	
	void insertServiceGroup(DbServiceGroup dbServiceGroup);
	List<DbServiceGroup> selectServiceGroupList(DbProject dbProject);
	
	void insertServer(DbServer dbServer);
	List<DbServer> selectServerList(DbServiceGroup dbServiceGroup);

	void insertRole(DbRole dbRole);
	List<DbRole> selectRoleList(DbProject dbProject);
}
