package jansible.mapper;

import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbApplyHistory;

import java.util.List;

public interface ApplyHistoryMapper {
	int insertDbApplyHistory(DbApplyHistory dbApplyHistory);
	List<DbApplyHistory> selectDbApplyHistoryList(ProjectKey projectKey);
	List<DbApplyHistory> selectDbApplyHistoryListByGroup(ServiceGroupKey serviceGroupKey);
	List<DbApplyHistory> selectDbApplyHistoryListByServer(ServerRelationKey serverRelationKey);
	DbApplyHistory selectDbApplyHistory(ApplyHistoryKey applyHistoryKey);
	void deleteApplyHistoryByProject(ProjectKey projectKey);

	List<String> selectTagNameList(ProjectKey projectKey);
}
