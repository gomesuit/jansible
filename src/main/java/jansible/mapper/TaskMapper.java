package jansible.mapper;

import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.TaskConditionalKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskConditional;
import jansible.model.database.DbTaskDetail;

import java.util.List;

public interface TaskMapper {
	void insertTask(DbTask dbTask);
	List<DbTask> selectTaskList(RoleKey roleKey);
	DbTask selectTask(TaskKey taskKey);
	void updateTask(DbTask dbTask);
	void deleteTask(TaskKey taskKey);
	void deleteTaskByRole(RoleKey roleKey);
	void deleteTaskByProject(ProjectKey projectKey);
	
	void insertTaskDetail(DbTaskDetail dbTaskDetail);
	List<DbTaskDetail> selectTaskDetailList(TaskKey taskKey);
	void deleteTaskDetail(TaskKey taskKey);
	void deleteTaskDetailByRole(RoleKey roleKey);
	void deleteTaskDetailByProject(ProjectKey projectKey);
	
	void insertDbTaskConditional(DbTaskConditional dbTaskConditional);
	List<DbTaskConditional> selectDbTaskConditionalList(TaskKey taskKey);
	DbTaskConditional selectDbTaskConditional(TaskConditionalKey taskConditionalKey);
	void deleteTaskConditional(TaskConditionalKey taskConditionalKey);
}
