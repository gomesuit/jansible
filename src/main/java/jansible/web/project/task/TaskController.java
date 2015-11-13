package jansible.web.project.task;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.TaskKey;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.model.yamldump.YamlModule;
import jansible.util.YamlDumper;
import jansible.web.module.ModuleService;
import jansible.web.project.ProjectService;
import jansible.web.project.RoleService;
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
	private ProjectService projectService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private YamlDumper yamlDumper;
	@Autowired
	private YamlService yamlService;
	@Autowired
	private RoleService roleService;
    
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
		
		DbTask dbTask = projectService.getTask(taskKey);
		String moduleName = dbTask.getModuleName();
		model.addAttribute("moduleName", moduleName);
		
		HtmlModule module = moduleService.getModule(moduleName);
	
		TaskDetailForm form = new TaskDetailForm(taskKey);
		form.setDescription(dbTask.getDescription());
		List<TaskParameter> taskParameterList = createBlankTaskParameterList(module);
		List<DbTaskDetail> dbTaskDetailList = projectService.getTaskDetailList(taskKey);
		mergeParameterList(taskParameterList, dbTaskDetailList);
		form.setTaskParameterList(taskParameterList);
		model.addAttribute("form", form);
		
		model.addAttribute("variableList", roleService.getDbRoleVariableList(taskKey));
		
	
		List<DbTask> dbTaskList = new ArrayList<>();
		dbTaskList.add(dbTask);
		List<YamlModule> modules = yamlService.createYamlModuleList(dbTaskList);
		model.addAttribute("yaskYaml", yamlDumper.dump(modules).replaceAll("\n", "<br />"));
		
	    return "project/task/top";
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
			TaskParameter taskParameter = new TaskParameter();
			taskParameter.setParameterName(htmlParameter.getName());
			taskParameter.setAddedVersion(htmlParameter.getAddedVersion());
			taskParameter.setRequired(htmlParameter.isRequired());
			taskParameter.setDefaultValue(htmlParameter.getDefaultValue());
			taskParameter.setChoices(htmlParameter.getChoices());
			taskParameter.setDescription(htmlParameter.getDescription());
			taskParameterList.add(taskParameter);
		}
		return taskParameterList;
	}

	@RequestMapping(value="/project/taskdetail/regist", method=RequestMethod.POST)
	private String registTaskDetail(@ModelAttribute TaskDetailForm form, HttpServletRequest request){
		projectService.updateTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
