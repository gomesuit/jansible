package jansible.web.project.group;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.web.project.GroupService;
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
		
    	//サーバ関連
		model.addAttribute("serverRelationForm", new ServerRelationForm(serviceGroupKey));
    	model.addAttribute("serverList", serverService.getServerListByEnvironment(serviceGroupKey));
		model.addAttribute("serverRelationList", groupService.getServerRelationList(serviceGroupKey));
    	model.addAttribute("serverRelationKey", new ServerRelationKey(serviceGroupKey));
    	
    	//ロール一覧
    	model.addAttribute("roleNameList", groupService.getRoleNameList(serviceGroupKey));
    	
    	//ロール関連
		model.addAttribute("roleRelationForm", new RoleRelationForm(serviceGroupKey));
		model.addAttribute("roleRelationList", groupService.getRoleRelationList(serviceGroupKey));
    	model.addAttribute("roleRelationKey", new RoleRelationKey(serviceGroupKey));
    	model.addAttribute("roleRelationOrderForm", new RoleRelationOrderForm(serviceGroupKey));
    	
    	//変数関連
		model.addAttribute("variableForm", new ServiceGroupVariableForm(serviceGroupKey));
		model.addAttribute("allVariableNameList", variableService.getAllDbVariableNameList(serviceGroupKey));
		model.addAttribute("groupVariableList", variableService.getDbServiceGroupVariableList(serviceGroupKey));
		model.addAttribute("serviceGroupVariableKey", new ServiceGroupVariableKey(serviceGroupKey));
		
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

	@RequestMapping(value="/project/roleRelation/delete", method=RequestMethod.POST)
	private String deleteRoleRelation(@ModelAttribute RoleRelationKey key, HttpServletRequest request){
		groupService.deleteRoleRelation(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/roleRelation/sort", method=RequestMethod.POST)
	private String sortRoleRelation(@ModelAttribute RoleRelationOrderForm form, HttpServletRequest request){
		groupService.modifyRoleRelationOrder(form);
		
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
	
	@RequestMapping(value="/project/serverRelation/regist", method=RequestMethod.POST)
	private String registServerRelation(@ModelAttribute ServerRelationForm form, HttpServletRequest request){
		groupService.registServerRelationDetail(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/serverRelation/delete", method=RequestMethod.POST)
	private String deleteServerRelation(@ModelAttribute ServerRelationKey key, HttpServletRequest request){
		groupService.deleteServerRelation(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
