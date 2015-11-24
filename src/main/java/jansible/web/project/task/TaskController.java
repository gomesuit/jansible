package jansible.web.project.task;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.TaskConditionalKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.web.module.ModuleService;
import jansible.web.project.RoleService;
import jansible.web.project.TaskService;
import jansible.web.project.VariableService;
import jansible.web.project.YamlService;
import jansible.web.project.task.TaskDetailForm;
import jansible.web.project.task.TaskParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private YamlService yamlService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private VariableService variableService;
	@Autowired
	private TaskService taskService;
    
    @RequestMapping("/task/view")
	private String viewTask(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "roleName", required = true) String roleName,
			@RequestParam(value = "taskId", required = true) int taskId,
			Model model){
		TaskKey taskKey = new TaskKey();
		taskKey.setProjectName(projectName);
		taskKey.setRoleName(roleName);
		taskKey.setTaskId(taskId);
		
		DbTask dbTask = taskService.getTask(taskKey);
		String moduleName = dbTask.getModuleName();
		model.addAttribute("moduleName", moduleName);
		
		// タスク詳細
		TaskDetailForm form = new TaskDetailForm(taskKey);
		form.setDescription(dbTask.getDescription());
		HtmlModule module = moduleService.getModule(moduleName);
		List<TaskParameter> taskParameterList = createBlankTaskParameterList(module);
		List<DbTaskDetail> dbTaskDetailList = taskService.getTaskDetailList(taskKey);
		mergeParameterList(taskParameterList, dbTaskDetailList);
		form.setTaskParameterList(taskParameterList);
		model.addAttribute("form", form);
		
		// Conditional関連
		model.addAttribute("taskConditionalForm", new TaskConditionalForm(taskKey));
		model.addAttribute("taskConditionalList", taskService.getTaskConditionalList(taskKey));
		model.addAttribute("taskConditionalKey", new TaskConditionalKey(taskKey));
		
		// yamlプレビュー
		model.addAttribute("taskPreview", yamlService.createTaskPreview(dbTask));
		
		// 変数一覧
		model.addAttribute("variableList", variableService.getDbRoleVariableList(taskKey));
		
	    return "project/task/top";
	}

	@RequestMapping(value="/project/taskdetail/regist", method=RequestMethod.POST)
	private String registTaskDetail(@ModelAttribute TaskDetailForm form, HttpServletRequest request){
		taskService.updateTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/taskConditional/regist", method=RequestMethod.POST)
	private String registTaskConditional(@ModelAttribute TaskConditionalForm form, HttpServletRequest request){
		taskService.registTaskConditional(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/taskConditional/delete", method=RequestMethod.POST)
	private String deleteTaskConditional(@ModelAttribute TaskConditionalKey key, HttpServletRequest request){
		taskService.deleteTaskConditional(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private void mergeParameterList(List<TaskParameter> taskParameterList, List<DbTaskDetail> dbTaskDetailList) {
		for(TaskParameter taskParameter : taskParameterList){
			String formParameterName = taskParameter.getParameterName();
			for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
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
