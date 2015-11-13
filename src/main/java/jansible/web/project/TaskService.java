package jansible.web.project;

import jansible.mapper.TaskMapper;
import jansible.model.common.RoleKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.web.project.task.TaskDetailForm;
import jansible.web.project.task.TaskForm;
import jansible.web.project.task.TaskParameter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private FileService fileService;

	public void deleteTask(TaskKey taskKey){
		taskMapper.deleteTask(taskKey);
		taskMapper.deleteTaskDetail(taskKey);
	}

	public void updateTask(TaskDetailForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.updateTask(dbTask);
		
		registTaskDetail(form);
		
		fileService.outputTaskData(form);
	}

	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.insertTask(dbTask);
	}

	public List<DbTask> getTaskList(RoleKey roleKey){
		return taskMapper.selectTaskList(roleKey);
	}

	public DbTask getTask(TaskKey taskKey){
		return taskMapper.selectTask(taskKey);
	}

	private void registTaskDetail(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
			taskMapper.insertTaskDetail(dbTaskDetail);
		}
	}

	public List<DbTaskDetail> getTaskDetailList(TaskKey taskKey){
		return taskMapper.selectTaskDetailList(taskKey);
	}

	private List<DbTaskDetail> createDbTaskDetailList(TaskDetailForm form) {
			List<DbTaskDetail> dbTaskDetailList = new ArrayList<>();
			List<TaskParameter> taskParameterList = form.getTaskParameterList();
			for(TaskParameter taskParameter : taskParameterList){
	//			if(StringUtils.isBlank(taskParameter.getParameterValue())){
	//				continue;
	//			}
				
				DbTaskDetail dbTaskDetail = createDbTaskDetail(form.getTaskId(), form.getProjectName(), form.getRoleName(), taskParameter);
				dbTaskDetailList.add(dbTaskDetail);
			}
			return dbTaskDetailList;
		}

	private DbTask createDbTask(TaskForm form) {
		DbTask dbTask = new DbTask(form);
		dbTask.setModuleName(form.getModuleName());
		dbTask.setDescription(form.getDescription());
		dbTask.setSort(form.getSort());
		return dbTask;
	}

	private DbTask createDbTask(TaskDetailForm form) {
		DbTask dbTask = new DbTask(form);
		dbTask.setDescription(form.getDescription());
		return dbTask;
	}

	private DbTaskDetail createDbTaskDetail(Integer taskId, String projectName, String roleName, TaskParameter taskParameter) {
		DbTaskDetail dbTaskDetail = new DbTaskDetail();
		dbTaskDetail.setTaskId(taskId);
		dbTaskDetail.setProjectName(projectName);
		dbTaskDetail.setRoleName(roleName);
		dbTaskDetail.setParameterName(taskParameter.getParameterName());
		dbTaskDetail.setParameterValue(taskParameter.getParameterValue());
		return dbTaskDetail;
	}
}
