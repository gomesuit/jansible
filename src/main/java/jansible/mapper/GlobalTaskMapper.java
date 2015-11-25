package jansible.mapper;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalTaskConditionalKey;
import jansible.model.common.GlobalTaskKey;
import jansible.model.database.DbGlobalTask;
import jansible.model.database.DbGlobalTaskConditional;
import jansible.model.database.DbGlobalTaskDetail;

import java.util.List;

public interface GlobalTaskMapper {
	void insertTask(DbGlobalTask dbGlobalTask);
	List<DbGlobalTask> selectTaskList(GlobalRoleKey globalRoleKey);
	DbGlobalTask selectTask(GlobalTaskKey globalTaskKey);
	void updateTaskDescription(DbGlobalTask dbGlobalTask);
	void updateTaskOrder(DbGlobalTask dbGlobalTask);
	void deleteTask(GlobalTaskKey globalTaskKey);
	void deleteTaskByRole(GlobalRoleKey globalRoleKey);
	
	void insertTaskDetail(DbGlobalTaskDetail dbGlobalTaskDetail);
	List<DbGlobalTaskDetail> selectTaskDetailList(GlobalTaskKey globalTaskKey);
	void deleteTaskDetail(GlobalTaskKey globalTaskKey);
	void deleteTaskDetailByRole(GlobalRoleKey globalRoleKey);
	
	void insertDbTaskConditional(DbGlobalTaskConditional dbGlobalTaskConditional);
	List<DbGlobalTaskConditional> selectDbTaskConditionalList(GlobalTaskKey globalTaskKey);
	DbGlobalTaskConditional selectDbTaskConditional(GlobalTaskConditionalKey globalTaskConditionalKey);
	void deleteTaskConditional(GlobalTaskConditionalKey globalTaskConditionalKey);
	void deleteTaskConditionalByTask(GlobalTaskKey globalTaskKey);
	void deleteTaskConditionalByRole(GlobalRoleKey globalRoleKey);
}
