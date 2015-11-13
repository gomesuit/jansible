package jansible.web.project.group;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.web.project.GroupService;
import jansible.web.project.ProjectService;
import jansible.web.project.RoleService;
import jansible.web.project.ServerService;
import jansible.web.project.VariableService;
import jansible.web.project.group.RoleRelationForm;
import jansible.web.project.group.ServiceGroupVariableForm;
import jansible.web.project.server.ServerForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private VariableService variableService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ServerService serverService;
    
    @RequestMapping("/serviceGroup/view")
	private String viewServiceGroup(
    		@RequestParam(value = "projectName", required = true) String projectName,
    		@RequestParam(value = "environmentName", required = true) String environmentName,
    		@RequestParam(value = "groupName", required = true) String groupName,
			Model model){
    	ServiceGroupKey serviceGroupKey = new ServiceGroupKey();
    	serviceGroupKey.setProjectName(projectName);
    	serviceGroupKey.setEnvironmentName(environmentName);
    	serviceGroupKey.setGroupName(groupName);
    	
		ServerForm serverForm = new ServerForm(serviceGroupKey);
		model.addAttribute("serverForm", serverForm);
		model.addAttribute("serverList", serverService.getServerList(serviceGroupKey));
		
		ServerKey serverKey = new ServerKey(serviceGroupKey);
		model.addAttribute("serverKey", serverKey);
		
		RoleRelationForm roleRelationForm = new RoleRelationForm(serviceGroupKey);
		model.addAttribute("roleRelationForm", roleRelationForm);
    	model.addAttribute("roleList", roleService.getRoleList(serviceGroupKey));
		model.addAttribute("roleRelationList", groupService.getRoleRelationList(serviceGroupKey));
		
		RoleRelationKey roleRelationKey = new RoleRelationKey(serviceGroupKey);
    	model.addAttribute("roleRelationKey", roleRelationKey);
		
		ServiceGroupVariableForm variableForm = new ServiceGroupVariableForm(serviceGroupKey);
		model.addAttribute("variableForm", variableForm);
		
		model.addAttribute("allVariableNameList", variableService.getAllDbVariableNameList(serviceGroupKey));
		model.addAttribute("groupVariableList", variableService.getDbServiceGroupVariableList(serviceGroupKey));
		
		ServiceGroupVariableKey serviceGroupVariableKey = new ServiceGroupVariableKey(serviceGroupKey);
		model.addAttribute("serviceGroupVariableKey", serviceGroupVariableKey);
		
	    return "project/service_group/top";
	}

    @RequestMapping(value="/project/server/regist", method=RequestMethod.POST)
	private String registServer(@ModelAttribute ServerForm form, HttpServletRequest request){
    	serverService.registServer(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/server/delete", method=RequestMethod.POST)
	private String deleteServer(@ModelAttribute ServerKey key, HttpServletRequest request){
		serverService.deleteServer(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/roleRelation/regist", method=RequestMethod.POST)
	private String registRoleRelation(@ModelAttribute RoleRelationForm form, HttpServletRequest request){
		groupService.registRoleRelationDetail(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/serviceGroupVariable/regist", method=RequestMethod.POST)
	private String registServiceGroupVariable(@ModelAttribute ServiceGroupVariableForm form, HttpServletRequest request){
		variableService.registServiceGroupVariable(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/serviceGroupVariable/delete", method=RequestMethod.POST)
	private String deleteServiceGroupVariable(@ModelAttribute ServiceGroupVariableKey key, HttpServletRequest request){
		variableService.deleteServiceGroupVariable(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
