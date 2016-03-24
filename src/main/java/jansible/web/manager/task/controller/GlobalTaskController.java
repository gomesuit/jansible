package jansible.web.manager.task.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.GlobalTaskConditionalKey;
import jansible.model.common.GlobalTaskKey;
import jansible.model.database.DbGlobalTask;
import jansible.model.database.DbGlobalTaskDetail;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.web.UrlTemplateMapper;
import jansible.web.manager.GlobalRoleService;
import jansible.web.manager.GlobalTaskService;
import jansible.web.manager.ManagerYamlService;
import jansible.web.manager.module.ModuleService;
import jansible.web.manager.task.form.TaskConditionalForm;
import jansible.web.manager.task.form.TaskDetailForm;
import jansible.web.manager.task.form.TaskParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GlobalTaskController {
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ManagerYamlService yamlService;
	@Autowired
	private GlobalRoleService roleService;
	@Autowired
	private GlobalTaskService taskService;
    
    @RequestMapping("/manager/task/view")
	private String viewTask(
			@RequestParam(value = "roleName", required = true) String roleName,
			@RequestParam(value = "taskId", required = true) int taskId,
			Model model, HttpServletRequest request){
    	GlobalTaskKey taskKey = new GlobalTaskKey();
		taskKey.setRoleName(roleName);
		taskKey.setTaskId(taskId);
		
		DbGlobalTask dbTask = taskService.getTask(taskKey);
		String moduleName = dbTask.getModuleName();
		model.addAttribute("moduleName", moduleName);
		
		// タスク詳細
		TaskDetailForm form = new TaskDetailForm(taskKey);
		form.setDescription(dbTask.getDescription());
		HtmlModule module = moduleService.getModule(moduleName);
		List<TaskParameter> taskParameterList = createBlankTaskParameterList(module);
		List<DbGlobalTaskDetail> dbTaskDetailList = taskService.getTaskDetailList(taskKey);
		mergeParameterList(taskParameterList, dbTaskDetailList);
		form.setTaskParameterList(taskParameterList);
		model.addAttribute("form", form);
		
		// Conditional関連
		model.addAttribute("taskConditionalForm", new TaskConditionalForm(taskKey));
		model.addAttribute("taskConditionalList", taskService.getTaskConditionalList(taskKey));
		model.addAttribute("taskConditionalKey", new GlobalTaskConditionalKey(taskKey));
		
		// yamlプレビュー
		model.addAttribute("taskPreview", yamlService.createTaskPreview(dbTask));
		
		// 変数一覧
		model.addAttribute("variableList", roleService.getDbRoleVariableList(taskKey));
		
		request.setAttribute("pageName", UrlTemplateMapper.MANAGER_TASK_DETAIL.getTemplatePath());
		return "common_frame";
	}

	@RequestMapping(value="/manager/taskdetail/regist", method=RequestMethod.POST)
	private String registTaskDetail(@ModelAttribute TaskDetailForm form, HttpServletRequest request){
		taskService.updateTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/taskConditional/regist", method=RequestMethod.POST)
	private String registTaskConditional(@ModelAttribute TaskConditionalForm form, HttpServletRequest request){
		taskService.registTaskConditional(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/taskConditional/delete", method=RequestMethod.POST)
	private String deleteTaskConditional(@ModelAttribute GlobalTaskConditionalKey key, HttpServletRequest request){
		taskService.deleteTaskConditional(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private void mergeParameterList(List<TaskParameter> taskParameterList, List<DbGlobalTaskDetail> dbTaskDetailList) {
		for(TaskParameter taskParameter : taskParameterList){
			String formParameterName = taskParameter.getParameterName();
			for(DbGlobalTaskDetail dbTaskDetail : dbTaskDetailList){
				String dbParameterName = dbTaskDetail.getParameterName();
				if(formParameterName.equals(dbParameterName)){
					taskParameter.setParameterValue(dbTaskDetail.getParameterValue());
				}
			}
		}
	}

	private List<TaskParameter> createBlankTaskParameterList(HtmlModule module) {
		List<TaskParameter> taskParameterList = new ArrayList<>();
		for(HtmlParameter htmlParameter : module.getParameterList()){
			TaskParameter taskParameter = createTaskParameter(htmlParameter);
			taskParameterList.add(taskParameter);
		}
		return taskParameterList;
	}
	
	private TaskParameter createTaskParameter(HtmlParameter htmlParameter){
		TaskParameter taskParameter = new TaskParameter();
		taskParameter.setParameterName(htmlParameter.getName());
		taskParameter.setAddedVersion(htmlParameter.getAddedVersion());
		taskParameter.setRequired(htmlParameter.isRequired());
		taskParameter.setDefaultValue(htmlParameter.getDefaultValue());
		taskParameter.setChoices(htmlParameter.getChoices());
		taskParameter.setDescription(htmlParameter.getDescription());
		return taskParameter;
	}
}
