package jansible.web.project;

import javax.servlet.http.HttpServletRequest;

import jansible.web.project.form.ProjectForm;
import jansible.web.project.form.RoleForm;
import jansible.web.project.form.ServerForm;
import jansible.web.project.form.ServiceGroupForm;

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
    	ServiceGroupForm serviceGroupForm = new ServiceGroupForm();
    	serviceGroupForm.setProjectName(projectName);
    	
    	model.addAttribute("form", serviceGroupForm);
    	model.addAttribute("serviceGroupList", projectService.getServiceGroupList(projectName));
        return "project/project/top";
    }

    @RequestMapping("/project/view/{projectName}/{groupName}")
    private String viewProject(@PathVariable String projectName, @PathVariable String groupName, Model model){
    	ServerForm serverForm = new ServerForm();
    	serverForm.setProjectName(projectName);
    	serverForm.setGroupName(groupName);
    	
    	model.addAttribute("form", serverForm);
    	model.addAttribute("serverList", projectService.getServerList(projectName, groupName));
        return "project/service_group/top";
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
