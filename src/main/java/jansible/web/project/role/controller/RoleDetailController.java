package jansible.web.project.role.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.web.UrlTemplateMapper;
import jansible.web.manager.module.ModuleService;
import jansible.web.project.RoleService;
import jansible.web.project.TaskService;
import jansible.web.project.VariableService;
import jansible.web.project.YamlService;
import jansible.web.project.role.form.GeneralFileForm;
import jansible.web.project.role.form.RoleVariableForm;
import jansible.web.project.role.form.TaskForm;
import jansible.web.project.role.form.TaskOrderForm;
import jansible.web.project.role.form.TaskView;
import jansible.web.project.role.form.UploadForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleDetailController {
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
    
    @RequestMapping("/project/role/view")
	private String viewRole(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "roleName", required = true) String roleName,
			Model model, HttpServletRequest request){
		RoleKey roleKey = new RoleKey();
		roleKey.setProjectName(projectName);
		roleKey.setRoleName(roleName);
		
		// タスク関連
		model.addAttribute("form", new TaskForm(roleKey));
		List<DbTask> dbTaskList = taskService.getTaskList(roleKey);
		List<TaskView> taskViewList = createTaskViewList(dbTaskList);
		model.addAttribute("taskList", taskViewList);
		model.addAttribute("taskKey", new TaskKey(roleKey));
		model.addAttribute("taskOrderForm", new TaskOrderForm(roleKey));
		
		// module名リスト
		model.addAttribute("moduleNameList", moduleService.getAvailableModuleList());

		// テンプレート・ファイル アップロード
		model.addAttribute("uploadForm", new UploadForm(roleKey));
		// テンプレート一覧
		model.addAttribute("templateList", roleService.getDbTemplateList(roleKey));
		// ファイル一覧
		model.addAttribute("fileList", roleService.getDbFileList(roleKey));
		// テンプレート・ファイル削除
		model.addAttribute("fileForm", new GeneralFileForm(roleKey));
		
		// 変数関連
		model.addAttribute("variableForm", new RoleVariableForm(roleKey));
		model.addAttribute("roleVariableKey", new RoleVariableKey(roleKey));
		model.addAttribute("variableList", variableService.getDbRoleVariableList(roleKey));
		
		request.setAttribute("pageName", UrlTemplateMapper.ROLE_DETAIL.getTemplatePath());
		return "common_frame";
	}

	@RequestMapping(value="/project/roleVariable/regist", method=RequestMethod.POST)
	private String registRoleVariable(@ModelAttribute RoleVariableForm form, HttpServletRequest request){
		variableService.registRoleVariable(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/roleVariable/delete", method=RequestMethod.POST)
	private String deleteRoleVariable(@ModelAttribute RoleVariableKey key, HttpServletRequest request){
		variableService.deleteRoleVariable(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private TaskView createTaskView(DbTask dbTask) {
		TaskView taskView = new TaskView();
		taskView.setTaskId(dbTask.getTaskId());
		taskView.setModuleName(dbTask.getModuleName());
		taskView.setDescription(dbTask.getDescription());
		List<DbTaskDetail> dbTaskDetailList = taskService.getTaskDetailList(dbTask);
		String parametersValue = yamlService.createTaskParametersValue(dbTask.getModuleName(), dbTaskDetailList);
		taskView.setParametersValue(parametersValue);
		return taskView;
	}

	private List<TaskView> createTaskViewList(List<DbTask> dbTaskList){
		List<TaskView> taskViewList = new ArrayList<>();
		for(DbTask dbTask : dbTaskList){
			TaskView taskView = createTaskView(dbTask);
			taskViewList.add(taskView);
		}
		return taskViewList;
	}

	@RequestMapping(value="/project/task/regist", method=RequestMethod.POST)
	private String registTask(@ModelAttribute TaskForm form, HttpServletRequest request){
		taskService.registTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/task/delete", method=RequestMethod.POST)
	private String deleteTask(@ModelAttribute TaskKey key, HttpServletRequest request){
		taskService.deleteTask(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/task/order", method=RequestMethod.POST)
	private String orderTask(@ModelAttribute TaskOrderForm form, HttpServletRequest request){
		taskService.modifyTaskOrder(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
