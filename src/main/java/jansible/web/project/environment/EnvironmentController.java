package jansible.web.project.environment;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;
import jansible.model.common.ServiceGroupKey;
import jansible.web.project.ProjectService;
import jansible.web.project.environment.EnvironmentVariableForm;
import jansible.web.project.group.ServiceGroupForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnvironmentController {
	@Autowired
	private ProjectService projectService;

	@RequestMapping("/environment/view")
	private String viewEnvironment(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "environmentName", required = true) String environmentName,
			Model model){
		EnvironmentKey environmentKey = new EnvironmentKey();
		environmentKey.setProjectName(projectName);
		environmentKey.setEnvironmentName(environmentName);
		
		ServiceGroupForm serviceGroupForm = new ServiceGroupForm(environmentKey);
		
		model.addAttribute("form", serviceGroupForm);
		model.addAttribute("serviceGroupList", projectService.getServiceGroupList(environmentKey));
		
		ServiceGroupKey serviceGroupKey = new ServiceGroupKey(environmentKey);
		model.addAttribute("serviceGroupKey", serviceGroupKey);
		
		EnvironmentVariableForm variableForm = new EnvironmentVariableForm(environmentKey);
		model.addAttribute("variableForm", variableForm);
		
		model.addAttribute("allVariableNameList", projectService.getAllDbVariableNameList(environmentKey));
		model.addAttribute("variableList", projectService.getDbEnvironmentVariableList(environmentKey));
		
		EnvironmentVariableKey environmentVariableKey = new EnvironmentVariableKey(environmentKey);
		model.addAttribute("environmentVariableKey", environmentVariableKey);
		
	    return "project/environment/top";
	}

	@RequestMapping(value="/project/group/regist", method=RequestMethod.POST)
    private String registServiceGroup(@ModelAttribute ServiceGroupForm form, HttpServletRequest request){
    	projectService.registServiceGroup(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/environmentVariable/regist", method=RequestMethod.POST)
    private String registEnvironmentVariable(@ModelAttribute EnvironmentVariableForm form, HttpServletRequest request){
    	projectService.registEnvironmentVariable(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/environmentVariable/delete", method=RequestMethod.POST)
    private String deleteEnvironmentVariable(@ModelAttribute EnvironmentVariableKey key, HttpServletRequest request){
    	projectService.deleteEnvironmentVariable(key);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

	@RequestMapping(value="/project/serviceGroup/delete", method=RequestMethod.POST)
    private String deleteServiceGroup(@ModelAttribute ServiceGroupKey key, HttpServletRequest request){
    	projectService.deleteServiceGroup(key);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
