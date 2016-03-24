package jansible.web.manager.role.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleVariableKey;
import jansible.model.common.GlobalTaskKey;
import jansible.model.database.DbGlobalTask;
import jansible.model.database.DbGlobalTaskDetail;
import jansible.web.UrlTemplateMapper;
import jansible.web.manager.GlobalRoleService;
import jansible.web.manager.GlobalTaskService;
import jansible.web.manager.ManagerGitService;
import jansible.web.manager.ManagerYamlService;
import jansible.web.manager.module.ModuleService;
import jansible.web.manager.role.form.GeneralFileForm;
import jansible.web.manager.role.form.GitForm;
import jansible.web.manager.role.form.RoleVariableForm;
import jansible.web.manager.role.form.TaskForm;
import jansible.web.manager.role.form.TaskOrderForm;
import jansible.web.manager.role.form.TaskView;
import jansible.web.manager.role.form.UploadForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GlobalRoleController {
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ManagerYamlService yamlService;
	@Autowired
	private GlobalRoleService roleService;
	@Autowired
	private GlobalTaskService taskService;
	@Autowired
	private ManagerGitService gitService;
    
    @RequestMapping("/manager/role/view")
	private String viewRole(
			@RequestParam(value = "roleName", required = true) String roleName,
			Model model, HttpServletRequest request){
    	GlobalRoleKey roleKey = new GlobalRoleKey();
		roleKey.setRoleName(roleName);
		
		// タスク関連
		model.addAttribute("form", new TaskForm(roleKey));
		List<DbGlobalTask> dbTaskList = taskService.getTaskList(roleKey);
		List<TaskView> taskViewList = createTaskViewList(dbTaskList);
		model.addAttribute("taskList", taskViewList);
		model.addAttribute("taskKey", new GlobalTaskKey(roleKey));
		model.addAttribute("taskOrderForm", new TaskOrderForm(roleKey));
		
		// module名リスト
		model.addAttribute("moduleNameList", moduleService.getModuleNameList());

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
		model.addAttribute("roleVariableKey", new GlobalRoleVariableKey(roleKey));
		model.addAttribute("variableList", roleService.getDbRoleVariableList(roleKey));
		
		// Git
		model.addAttribute("gitForm", new GitForm(roleKey));
		model.addAttribute("role", roleService.getRole(roleKey));
		model.addAttribute("tagList", roleService.getRoleTagList(roleKey));
		
		request.setAttribute("pageName", UrlTemplateMapper.MANAGER_ROLE_DETAIL.getTemplatePath());
		return "common_frame";
	}

	@RequestMapping(value="/manager/roleVariable/regist", method=RequestMethod.POST)
	private String registRoleVariable(@ModelAttribute RoleVariableForm form, HttpServletRequest request){
		roleService.registRoleVariable(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/roleVariable/delete", method=RequestMethod.POST)
	private String deleteRoleVariable(@ModelAttribute GlobalRoleVariableKey key, HttpServletRequest request){
		roleService.deleteRoleVariable(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private TaskView createTaskView(DbGlobalTask dbTask) {
		TaskView taskView = new TaskView();
		taskView.setTaskId(dbTask.getTaskId());
		taskView.setModuleName(dbTask.getModuleName());
		taskView.setDescription(dbTask.getDescription());
		List<DbGlobalTaskDetail> dbTaskDetailList = taskService.getTaskDetailList(dbTask);
		String parametersValue = yamlService.createTaskParametersValue(dbTask.getModuleName(), dbTaskDetailList);
		taskView.setParametersValue(parametersValue);
		return taskView;
	}

	private List<TaskView> createTaskViewList(List<DbGlobalTask> dbTaskList){
		List<TaskView> taskViewList = new ArrayList<>();
		for(DbGlobalTask dbTask : dbTaskList){
			TaskView taskView = createTaskView(dbTask);
			taskViewList.add(taskView);
		}
		return taskViewList;
	}

	@RequestMapping(value="/manager/task/regist", method=RequestMethod.POST)
	private String registTask(@ModelAttribute TaskForm form, HttpServletRequest request){
		taskService.registTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/task/delete", method=RequestMethod.POST)
	private String deleteTask(@ModelAttribute GlobalTaskKey key, HttpServletRequest request){
		taskService.deleteTask(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/task/order", method=RequestMethod.POST)
	private String orderTask(@ModelAttribute TaskOrderForm form, HttpServletRequest request){
		taskService.modifyTaskOrder(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/git/commit", method=RequestMethod.POST)
	private String commitGit(@ModelAttribute GitForm form, HttpServletRequest request) throws Exception{
		gitService.commitGit(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/git/tagging", method=RequestMethod.POST)
	private String taggingGit(@ModelAttribute GitForm form, HttpServletRequest request) throws Exception{
		gitService.tagAndPush(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
