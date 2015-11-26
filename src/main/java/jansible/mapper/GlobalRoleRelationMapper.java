package jansible.mapper;

import jansible.model.common.GlobalRoleRelationKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleRelation;

import java.util.List;

public interface GlobalRoleRelationMapper {
	void insertRoleRelation(DbGlobalRoleRelation dbGlobalRoleRelation);
	List<DbGlobalRoleRelation> selectRoleRelationList(ProjectKey key);
	DbGlobalRoleRelation selectRoleRelation(GlobalRoleRelationKey key);
	void deleteRoleRelation(GlobalRoleRelationKey key);
	void deleteRoleRelationByProject(ProjectKey key);

	List<DbGlobalRole> selectRoleList();
	String selectNewestTagName(String roleName);
	String selectUri(String roleName);
	List<String> selectTagNameList(String roleName);
}
