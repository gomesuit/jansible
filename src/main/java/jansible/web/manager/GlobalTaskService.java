package jansible.web.manager;

import jansible.mapper.GlobalTaskMapper;
import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalTaskConditionalKey;
import jansible.model.common.GlobalTaskKey;
import jansible.model.database.DbGlobalTask;
import jansible.model.database.DbGlobalTaskConditional;
import jansible.model.database.DbGlobalTaskDetail;
import jansible.util.DbCommonUtils;
import jansible.web.manager.role.form.TaskForm;
import jansible.web.manager.role.form.TaskOrderForm;
import jansible.web.manager.role.form.TaskOrderType;
import jansible.web.manager.task.form.TaskConditionalForm;
import jansible.web.manager.task.form.TaskDetailForm;
import jansible.web.manager.task.form.TaskParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalTaskService {
	@Autowired
	private GlobalTaskMapper taskMapper;
	@Autowired
	private ManagerFileService fileService;

	public void registTask(TaskForm form) {
		DbGlobalTask dbTask = createDbTask(form);
		taskMapper.insertTask(dbTask);
	}

	public void deleteTask(GlobalTaskKey key){
		taskMapper.deleteTask(key);
		taskMapper.deleteTaskDetail(key);
		taskMapper.deleteTaskConditionalByTask(key);
		
		List<DbGlobalTask> dbTaskList = taskMapper.selectTaskList(key);
		DbCommonUtils.sortRoleRelation(dbTaskList);
		registTaskList(dbTaskList);
		
		fileService.outputTaskData(key);
	}

	public void updateTask(TaskDetailForm form) {
		DbGlobalTask dbTask = createDbTask(form);
		taskMapper.updateTaskDescription(dbTask);
		
		registTaskDetail(form);
		
		fileService.outputTaskData(form);
	}

	public List<DbGlobalTask> getTaskList(GlobalRoleKey roleKey){
		return taskMapper.selectTaskList(roleKey);
	}

	public DbGlobalTask getTask(GlobalTaskKey key){
		return taskMapper.selectTask(key);
	}

	private void registTaskDetail(TaskDetailForm form) {
		List<DbGlobalTaskDetail> dbTaskDetailList = createDbTaskDetailList(form);
		for(DbGlobalTaskDetail dbTaskDetail : dbTaskDetailList){
			taskMapper.insertTaskDetail(dbTaskDetail);
		}
	}

	public List<DbGlobalTaskDetail> getTaskDetailList(GlobalTaskKey taskKey){
		return taskMapper.selectTaskDetailList(taskKey);
	}
	
	public void registTaskConditional(TaskConditionalForm form) {
		DbGlobalTaskConditional dbTaskConditional = new DbGlobalTaskConditional(form);
		dbTaskConditional.setConditionalValue(form.getConditionalValue());
		taskMapper.insertDbTaskConditional(dbTaskConditional);
		
		fileService.outputTaskData(form);
	}
	
	public List<DbGlobalTaskConditional> getTaskConditionalList(GlobalTaskKey key){
		return taskMapper.selectDbTaskConditionalList(key);
	}
	
	public DbGlobalTaskConditional getTaskConditional(GlobalTaskConditionalKey key){
		return taskMapper.selectDbTaskConditional(key);
	}
	
	public void deleteTaskConditional(GlobalTaskConditionalKey key){
		taskMapper.deleteTaskConditional(key);
		fileService.outputTaskData(key);
	}

	private List<DbGlobalTaskDetail> createDbTaskDetailList(TaskDetailForm form) {
			List<DbGlobalTaskDetail> dbTaskDetailList = new ArrayList<>();
			List<TaskParameter> taskParameterList = form.getTaskParameterList();
			for(TaskParameter taskParameter : taskParameterList){
	//			if(StringUtils.isBlank(taskParameter.getParameterValue())){
	//				continue;
	//			}
				
				DbGlobalTaskDetail dbTaskDetail = createDbTaskDetail(form, taskParameter);
				dbTaskDetailList.add(dbTaskDetail);
			}
			return dbTaskDetailList;
		}

	private DbGlobalTask createDbTask(TaskForm form) {
		DbGlobalTask dbTask = new DbGlobalTask(form);
		dbTask.setModuleName(form.getModuleName());
		dbTask.setDescription(form.getDescription());
		dbTask.setSort(taskMapper.selectTaskList(form).size() + 1);
		return dbTask;
	}

	private DbGlobalTask createDbTask(TaskDetailForm form) {
		DbGlobalTask dbTask = new DbGlobalTask(form);
		dbTask.setDescription(form.getDescription());
		return dbTask;
	}

	private DbGlobalTaskDetail createDbTaskDetail(TaskDetailForm form, TaskParameter taskParameter) {
		DbGlobalTaskDetail dbTaskDetail = new DbGlobalTaskDetail(form);
		dbTaskDetail.setParameterName(taskParameter.getParameterName());
		dbTaskDetail.setParameterValue(taskParameter.getParameterValue());
		return dbTaskDetail;
	}

	private void registTaskList(List<DbGlobalTask> dbTaskList){
		for(DbGlobalTask dbTask : dbTaskList){
			taskMapper.updateTaskOrder(dbTask);
		}
	}
	
	public void modifyTaskOrder(TaskOrderForm form){
		List<DbGlobalTask> dbTaskList = getOrderDbTaskList(form);
		
		if(dbTaskList != null){
			registTaskList(dbTaskList);
			fileService.outputTaskData(form);
		}
	}

	private List<DbGlobalTask> getOrderDbTaskList(TaskOrderForm form){
		List<DbGlobalTask> dbTaskList = taskMapper.selectTaskList(form);
		int targetIndex = dbTaskList.indexOf(new DbGlobalTask(form));
		
		if(form.getOrderType() == TaskOrderType.UP){
			if(targetIndex - 1 < 0){
				return null;
			}
			Collections.swap(dbTaskList, targetIndex, targetIndex - 1);
		}else if((form.getOrderType() == TaskOrderType.DOWN)){
			if(targetIndex + 1 >= dbTaskList.size()){
				return null;
			}
			Collections.swap(dbTaskList, targetIndex, targetIndex + 1);
		}else{
			return null;
		}

		DbCommonUtils.sortRoleRelation(dbTaskList);
		
		return dbTaskList;
	}
}
