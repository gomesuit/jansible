package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.web.module.ModuleService;
import jansible.web.project.form.EnvironmentForm;
import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.RoleRelationForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServiceGroupForm;
import jansible.web.project.form.TaskDetailForm;
import jansible.web.project.form.TaskForm;
import jansible.web.project.form.TaskParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ModuleService moduleService;
    
    @RequestMapping("/project/top")
    private String top(Model model){
    	model.addAttribute("form", new ProjectForm());
    	model.addAttribute("projectList", projectService.getProjectList());
        return "project/top";
    }

    @RequestMapping("/project/view/{projectName}/{environmentName}/{groupName}")
	private String viewServiceGroup(@PathVariable String projectName, @PathVariable String environmentName, @PathVariable String groupName, Model model){
		ServerForm serverForm = new ServerForm();
		serverForm.setProjectName(projectName);
		serverForm.setEnvironmentName(environmentName);
		serverForm.setGroupName(groupName);
		model.addAttribute("serverForm", serverForm);
		model.addAttribute("serverList", projectService.getServerList(projectName, environmentName, groupName));
		
		RoleRelationForm roleRelationForm = new RoleRelationForm();
		roleRelationForm.setProjectName(projectName);
		roleRelationForm.setEnvironmentName(environmentName);
		roleRelationForm.setGroupName(groupName);
		model.addAttribute("roleRelationForm", roleRelationForm);
    	model.addAttribute("roleList", projectService.getRoleList(projectName));
		model.addAttribute("roleRelationList", projectService.getRoleRelationList(projectName, environmentName, groupName));
	    return "project/service_group/top";
	}

	@RequestMapping("/project/view/{projectName}")
    private String viewProject(@PathVariable String projectName, Model model){
    	EnvironmentForm environmentForm = new EnvironmentForm();
    	environmentForm.setProjectName(projectName);
    	model.addAttribute("environmentForm", environmentForm);
    	model.addAttribute("environmentList", projectService.getEnvironmentList(projectName));
    	
    	RoleForm roleForm = new RoleForm();
    	roleForm.setProjectName(projectName);
    	model.addAttribute("roleForm", roleForm);
    	model.addAttribute("roleList", projectService.getRoleList(projectName));
        return "project/project/top";
    }

    @RequestMapping("/project/view/{projectName}/{environmentName}")
    private String viewEnvironment(@PathVariable String projectName, @PathVariable String environmentName, Model model){
    	ServiceGroupForm serviceGroupForm = new ServiceGroupForm();
    	serviceGroupForm.setProjectName(projectName);
    	serviceGroupForm.setEnvironmentName(environmentName);
    	
    	model.addAttribute("form", serviceGroupForm);
    	model.addAttribute("serviceGroupList", projectService.getServiceGroupList(projectName, environmentName));
        return "project/environment/top";
    }

    @RequestMapping("/project/view/role/{projectName}/{roleName}")
    private String viewRole(@PathVariable String projectName, @PathVariable String roleName, Model model){
    	TaskForm form = new TaskForm();
    	form.setProjectName(projectName);
    	form.setRoleName(roleName);
    	
    	model.addAttribute("form", form);
    	model.addAttribute("taskList", projectService.getTaskList(projectName, roleName));
    	
    	// module名リスト
    	model.addAttribute("moduleNameList", moduleService.getModuleNameList());
        return "project/role/top";
    }
    
    @RequestMapping("/project/view/role/{projectName}/{roleName}/{taskId}")
    private String viewTask(@PathVariable String projectName, @PathVariable String roleName, @PathVariable int taskId, Model model){
    	DbTask dbTask = projectService.getTask(projectName, roleName, taskId);
    	String moduleName = dbTask.getModuleName();
    	
    	HtmlModule module = moduleService.getModule(moduleName);
    	model.addAttribute("module", module);

    	TaskDetailForm form = new TaskDetailForm();
    	form.setProjectName(projectName);
    	form.setRoleName(roleName);
    	form.setTaskId(taskId);
    	List<TaskParameter> taskParameterList = createBlankTaskParameterList(module);
    	List<DbTaskDetail> dbTaskDetailList = projectService.getTaskDetailList(projectName, roleName, taskId);
    	mergeParameterList(taskParameterList, dbTaskDetailList);
    	form.setTaskParameterList(taskParameterList);
    	model.addAttribute("form", form);
    	
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
    		taskParameterList.add(taskParameter);
    	}
		return taskParameterList;
	}

	@RequestMapping(value="/project/regist", method=RequestMethod.POST)
	private String registProject(@ModelAttribute ProjectForm form, HttpServletRequest request){
		projectService.registProject(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/task/regist", method=RequestMethod.POST)
    private String registTask(@ModelAttribute TaskForm form, HttpServletRequest request){
    	projectService.registTask(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
    
    @RequestMapping(value="/project/taskdetail/regist", method=RequestMethod.POST)
    private String registTaskDetail(@ModelAttribute TaskDetailForm form, HttpServletRequest request){
    	projectService.registTaskDetail(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
    
    @RequestMapping(value="/project/environment/regist", method=RequestMethod.POST)
    private String registEnvironment(@ModelAttribute EnvironmentForm form, HttpServletRequest request){
    	projectService.registEnvironment(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
    
    @RequestMapping(value="/project/group/regist", method=RequestMethod.POST)
    private String registServiceGroup(@ModelAttribute ServiceGroupForm form, HttpServletRequest request){
    	projectService.registServiceGroup(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/server/regist", method=RequestMethod.POST)
    private String registServer(@ModelAttribute ServerForm form, HttpServletRequest request){
    	projectService.registServer(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/roleRelation/regist", method=RequestMethod.POST)
    private String registRoleRelation(@ModelAttribute RoleRelationForm form, HttpServletRequest request){
    	projectService.registRoleRelationDetail(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/role/regist", method=RequestMethod.POST)
    private String registRole(@ModelAttribute RoleForm form, HttpServletRequest request){
    	projectService.registRole(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}