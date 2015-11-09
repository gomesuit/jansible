package jansible.mapper;

import jansible.model.common.RoleKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;

import java.util.List;

public interface TaskMapper {
	void insertTask(DbTask dbTask);
	List<DbTask> selectTaskList(RoleKey roleKey);
	DbTask selectTask(TaskKey taskKey);
	void updateTask(DbTask dbTask);
	
	void insertTaskDetail(DbTaskDetail dbTaskDetail);
	List<DbTaskDetail> selectTaskDetailList(TaskKey taskKey);
}
