package jansible.web.project;

import javax.servlet.http.HttpServletRequest;

import jansible.web.project.form.EnvironmentForm;
import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServiceGroupForm;
import jansible.web.project.form.TaskForm;

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
    
    @RequestMapping("/project/top")
    private String top(Model model){
    	model.addAttribute("form", new ProjectForm());
    	model.addAttribute("projectList", projectService.getProjectList());
        return "project/top";
    }

    @RequestMapping(value="/project/regist", method=RequestMethod.POST)
    private String registProject(@ModelAttribute ProjectForm form, HttpServletRequest request){
    	projectService.registProject(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
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
    	model.addAttribute("moduleNameList", projectService.getModuleNameList());
        return "project/role/top";
    }
    
    @RequestMapping("/project/view/role/{projectName}/{roleName}/{taskId}")
    private String viewTask(@PathVariable String projectName, @PathVariable String roleName, @PathVariable int taskId, Model model){
        return "project/task/top";
    }

    @RequestMapping("/project/view/{projectName}/{environmentName}/{groupName}")
    private String viewProject(@PathVariable String projectName, @PathVariable String environmentName, @PathVariable String groupName, Model model){
    	ServerForm serverForm = new ServerForm();
    	serverForm.setProjectName(projectName);
    	serverForm.setEnvironmentName(environmentName);
    	serverForm.setGroupName(groupName);
    	
    	model.addAttribute("form", serverForm);
    	model.addAttribute("serverList", projectService.getServerList(projectName, environmentName, groupName));
        return "project/service_group/top";
    }
    
    @RequestMapping(value="/project/task/regist", method=RequestMethod.POST)
    private String registTask(@ModelAttribute TaskForm form, HttpServletRequest request){
    	projectService.registTask(form);
    	
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

    @RequestMapping(value="/project/role/regist", method=RequestMethod.POST)
    private String registRole(@ModelAttribute RoleForm form, HttpServletRequest request){
    	projectService.registRole(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
