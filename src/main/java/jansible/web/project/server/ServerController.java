package jansible.web.project.server;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerVariableKey;
import jansible.web.project.ProjectService;
import jansible.web.project.server.ServerVariableForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ServerController {
	@Autowired
	private ProjectService projectService;
    
    @RequestMapping("/server/view")
	private String viewServer(
    		@RequestParam(value = "projectName", required = true) String projectName,
    		@RequestParam(value = "environmentName", required = true) String environmentName,
    		@RequestParam(value = "groupName", required = true) String groupName,
    		@RequestParam(value = "serverName", required = true) String serverName,
			Model model) {
    	
    	ServerKey serverKey = new ServerKey();
    	serverKey.setProjectName(projectName);
    	serverKey.setEnvironmentName(environmentName);
    	serverKey.setGroupName(groupName);
    	serverKey.setServerName(serverName);

    	ServerVariableForm variableForm = new ServerVariableForm(serverKey);
		model.addAttribute("variableForm", variableForm);
		
		model.addAttribute("allVariableNameList", projectService.getAllDbVariableNameList(serverKey));
		model.addAttribute("variableList", projectService.getDbServerVariableList(serverKey));
		
		ServerVariableKey serverVariableKey = new ServerVariableKey(serverKey);
		model.addAttribute("serverVariableKey", serverVariableKey);

		return "project/server/top";
	}

	@RequestMapping(value="/project/roleRelation/delete", method=RequestMethod.POST)
    private String deleteRoleRelation(@ModelAttribute RoleRelationKey key, HttpServletRequest request){
    	projectService.deleteRoleRelation(key);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/serverVariable/regist", method=RequestMethod.POST)
    private String registServerVariable(@ModelAttribute ServerVariableForm form, HttpServletRequest request){
    	projectService.registServerVariable(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/serverVariable/delete", method=RequestMethod.POST)
    private String deleteServerVariable(@ModelAttribute ServerVariableKey key, HttpServletRequest request){
    	projectService.deleteServerVariable(key);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
