package jansible.web.project.group.controller;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.model.database.DbServiceGroup;
import jansible.web.UrlTemplateMapper;
import jansible.web.project.GroupService;
import jansible.web.project.ServerService;
import jansible.web.project.VariableService;
import jansible.web.project.group.form.RoleRelationForm;
import jansible.web.project.group.form.RoleRelationOrderForm;
import jansible.web.project.group.form.ServerRelationForm;
import jansible.web.project.group.form.ServiceGroupDescriptionForm;
import jansible.web.project.group.form.ServiceGroupVariableForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupRelationController {
	@Autowired
	private VariableService variableService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ServerService serverService;
    
    @RequestMapping("/project/group/detail")
	private String viewServiceGroup(
    		@RequestParam(value = "projectName", required = true) String projectName,
    		@RequestParam(value = "environmentName", required = true) String environmentName,
    		@RequestParam(value = "groupName", required = true) String groupName,
			Model model,
			HttpServletRequest request){
    	ServiceGroupKey serviceGroupKey = new ServiceGroupKey();
    	serviceGroupKey.setProjectName(projectName);
    	serviceGroupKey.setEnvironmentName(environmentName);
    	serviceGroupKey.setGroupName(groupName);
    	
    	// Description
    	DbServiceGroup serviceGroup = groupService.getServiceGroup(serviceGroupKey);
		ServiceGroupDescriptionForm serviceGroupDescriptionForm = new ServiceGroupDescriptionForm(serviceGroupKey);
		serviceGroupDescriptionForm.setGroupName(serviceGroupKey.getGroupName());
		serviceGroupDescriptionForm.setDescription(serviceGroup.getDescription());
		model.addAttribute("serviceGroupDescriptionForm", serviceGroupDescriptionForm);
    	
    	//サーバ関連
		model.addAttribute("serverRelationForm", new ServerRelationForm(serviceGroupKey));
    	model.addAttribute("serverList", serverService.getServerListByEnvironment(serviceGroupKey));
		model.addAttribute("serverRelationList", groupService.getServerRelationList(serviceGroupKey));
    	model.addAttribute("serverRelationKey", new ServerRelationKey(serviceGroupKey));
    	
    	//ロール選択肢
    	model.addAttribute("roleList", groupService.getRoleListWithGlobalRole(serviceGroupKey));
    	
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
		
		request.setAttribute("pageName", UrlTemplateMapper.GROUP_DETAIL.getTemplatePath());
		return "common_frame";
	}

    @RequestMapping(value="/project/groupDescription/regist", method=RequestMethod.POST)
	private String registGroupDescription(@ModelAttribute ServiceGroupDescriptionForm form, HttpServletRequest request){
		groupService.updateServiceGroupDescription(form);
		
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
