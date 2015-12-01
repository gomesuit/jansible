package jansible.web.project;

import jansible.mapper.TaskMapper;
import jansible.model.common.RoleKey;
import jansible.model.common.TaskConditionalKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskConditional;
import jansible.model.database.DbTaskDetail;
import jansible.util.DbCommonUtils;
import jansible.web.project.role.TaskForm;
import jansible.web.project.role.TaskOrderForm;
import jansible.web.project.role.TaskOrderType;
import jansible.web.project.task.TaskConditionalForm;
import jansible.web.project.task.TaskDetailForm;
import jansible.web.project.task.TaskParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class TaskService {
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private FileService fileService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public void registTask(TaskForm form) {
		DbTask dbTask = createDbTask(form);
		taskMapper.insertTask(dbTask);
	}

	public void deleteTask(TaskKey taskKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			taskMapper.deleteTask(taskKey);
			taskMapper.deleteTaskDetail(taskKey);
			taskMapper.deleteTaskConditionalByTask(taskKey);
			
			List<DbTask> dbTaskList = taskMapper.selectTaskList(taskKey);
			DbCommonUtils.sortRoleRelation(dbTaskList);
			registTaskList(dbTaskList);
			
			fileService.outputTaskData(taskKey);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void updateTask(TaskDetailForm form) {
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);
    	
		try {	
			DbTask dbTask = createDbTask(form);
			taskMapper.updateTaskDescription(dbTask);
			
			registTaskDetail(form);
			
			fileService.outputTaskData(form);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
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
	
	public void registTaskConditional(TaskConditionalForm form) {
		DbTaskConditional dbTaskConditional = new DbTaskConditional(form);
		dbTaskConditional.setConditionalValue(form.getConditionalValue());
		taskMapper.insertDbTaskConditional(dbTaskConditional);
		
		fileService.outputTaskData(form);
	}
	
	public List<DbTaskConditional> getTaskConditionalList(TaskKey taskKey){
		return taskMapper.selectDbTaskConditionalList(taskKey);
	}
	
	public DbTaskConditional getTaskConditional(TaskConditionalKey taskConditionalKey){
		return taskMapper.selectDbTaskConditional(taskConditionalKey);
	}
	
	public void deleteTaskConditional(TaskConditionalKey taskConditionalKey){
		taskMapper.deleteTaskConditional(taskConditionalKey);
		fileService.outputTaskData(taskConditionalKey);
	}

	private List<DbTaskDetail> createDbTaskDetailList(TaskDetailForm form) {
		List<DbTaskDetail> dbTaskDetailList = new ArrayList<>();
		List<TaskParameter> taskParameterList = form.getTaskParameterList();
		for(TaskParameter taskParameter : taskParameterList){				
			DbTaskDetail dbTaskDetail = createDbTaskDetail(form, taskParameter);
			dbTaskDetailList.add(dbTaskDetail);
		}
		return dbTaskDetailList;
	}

	private DbTask createDbTask(TaskForm form) {
		DbTask dbTask = new DbTask(form);
		dbTask.setModuleName(form.getModuleName());
		dbTask.setDescription(form.getDescription());
		dbTask.setSort(taskMapper.selectTaskList(form).size() + 1);
		return dbTask;
	}

	private DbTask createDbTask(TaskDetailForm form) {
		DbTask dbTask = new DbTask(form);
		dbTask.setDescription(form.getDescription());
		return dbTask;
	}

	private DbTaskDetail createDbTaskDetail(TaskDetailForm form, TaskParameter taskParameter) {
		DbTaskDetail dbTaskDetail = new DbTaskDetail(form);
		dbTaskDetail.setParameterName(taskParameter.getParameterName());
		dbTaskDetail.setParameterValue(taskParameter.getParameterValue());
		return dbTaskDetail;
	}

	private void registTaskList(List<DbTask> dbTaskList){
		for(DbTask dbTask : dbTaskList){
			taskMapper.updateTaskOrder(dbTask);
		}
	}
	
	public void modifyTaskOrder(TaskOrderForm form){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);
    	
		try {
			List<DbTask> dbTaskList = getOrderDbTaskList(form);
			
			if(dbTaskList != null){
				registTaskList(dbTaskList);
				fileService.outputTaskData(form);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
	}

	private List<DbTask> getOrderDbTaskList(TaskOrderForm form){
		List<DbTask> dbTaskList = taskMapper.selectTaskList(form);
		int targetIndex = dbTaskList.indexOf(new DbTask(form));
		
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
