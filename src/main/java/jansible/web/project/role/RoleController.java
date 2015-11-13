package jansible.web.project.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.yamldump.YamlModule;
import jansible.web.module.ModuleService;
import jansible.web.project.ProjectService;
import jansible.web.project.RoleService;
import jansible.web.project.TaskService;
import jansible.web.project.VariableService;
import jansible.web.project.YamlService;
import jansible.web.project.role.GeneralFileForm;
import jansible.web.project.role.RoleVariableForm;
import jansible.web.project.role.UploadForm;
import jansible.web.project.task.TaskForm;
import jansible.web.project.task.TaskView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {
	@Autowired
	private ProjectService projectService;
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
    
    @RequestMapping("/role/view")
	private String viewRole(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "roleName", required = true) String roleName,
			Model model){
		RoleKey roleKey = new RoleKey();
		roleKey.setProjectName(projectName);
		roleKey.setRoleName(roleName);
		
		TaskForm form = new TaskForm(roleKey);
		
		model.addAttribute("form", form);
		List<DbTask> dbTaskList = taskService.getTaskList(roleKey);
		List<TaskView> taskViewList = createTaskViewList(dbTaskList);
		model.addAttribute("taskList", taskViewList);
		
		TaskKey taskKey = new TaskKey(roleKey);
		model.addAttribute("taskKey", taskKey);
		
		// module名リスト
		model.addAttribute("moduleNameList", moduleService.getModuleNameList());
		
		UploadForm uploadForm = new UploadForm(roleKey);
		model.addAttribute("uploadForm", uploadForm);
	
		model.addAttribute("templateList", roleService.getDbTemplateList(roleKey));
		model.addAttribute("fileList", roleService.getDbFileList(roleKey));
		
		GeneralFileForm fileForm = new GeneralFileForm(roleKey);
		model.addAttribute("fileForm", fileForm);
		
		RoleVariableForm roleVariableForm = new RoleVariableForm(roleKey);
		model.addAttribute("variableForm", roleVariableForm);
	
		RoleVariableKey roleVariableKey = new RoleVariableKey(roleKey);
		model.addAttribute("roleVariableKey", roleVariableKey);
		
		model.addAttribute("variableList", variableService.getDbRoleVariableList(roleKey));
		
	    return "project/role/top";
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
		YamlModule yamlModule = new YamlModule(dbTask.getModuleName(), yamlService.createParameters(dbTaskDetailList));
		yamlModule.setDescription(dbTask.getDescription());
		taskView.setParametersValue(yamlModule.getParameters().toString());
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
}
