package jansible.mapper;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbServiceGroup;

import java.util.List;

public interface ServiceGroupMapper {
	void insertServiceGroup(DbServiceGroup dbServiceGroup);
	List<DbServiceGroup> selectServiceGroupList(EnvironmentKey environmentKey);
	void deleteServiceGroup(ServiceGroupKey serviceGroupKey);
	void deleteServiceGroupByEnvironment(EnvironmentKey environmentKey);
	
	void insertDbRoleRelation(DbRoleRelation dbRoleRelation);
	List<DbRoleRelation> selectDbRoleRelationList(ServiceGroupKey serviceGroupKey);
	void deleteDbRoleRelation(RoleRelationKey roleRelationKey);
	void deleteDbRoleRelationByServiceGroup(ServiceGroupKey serviceGroupKey);
	void deleteDbRoleRelationByEnvironment(EnvironmentKey environmentKey);
}