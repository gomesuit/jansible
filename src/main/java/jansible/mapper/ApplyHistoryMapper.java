package jansible.mapper;

import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbApplyHistory;

import java.util.List;

public interface ApplyHistoryMapper {
	void insertDbApplyHistory(DbApplyHistory dbApplyHistory);
	List<DbApplyHistory> selectDbApplyHistoryList(ProjectKey projectKey);
	DbApplyHistory selectDbApplyHistory(ApplyHistoryKey applyHistoryKey);
}