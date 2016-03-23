package jansible.web.project;

import jansible.mapper.ApplyHistoryMapper;
import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbApplyHistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
	@Autowired
	private ApplyHistoryMapper applyHistoryMapper;

	public List<DbApplyHistory> getDbApplyHistoryList(ProjectKey projectKey){
		return applyHistoryMapper.selectDbApplyHistoryList(projectKey);
	}
	
	public List<DbApplyHistory> getDbApplyHistoryListByGroup(ServiceGroupKey serviceGroupKey){
		return applyHistoryMapper.selectDbApplyHistoryListByGroup(serviceGroupKey);
	}
	
	public List<DbApplyHistory> getDbApplyHistoryListByServer(ServerRelationKey serverRelationKey){
		return applyHistoryMapper.selectDbApplyHistoryListByServer(serverRelationKey);
	}

	public DbApplyHistory getDbApplyHistory(ApplyHistoryKey applyHistoryKey){
		return applyHistoryMapper.selectDbApplyHistory(applyHistoryKey);
	}

	public List<String> getTagNameList(ProjectKey projectKey){
		return applyHistoryMapper.selectTagNameList(projectKey);
	}
}
