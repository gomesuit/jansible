package jansible.web.project.role.controller;

import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.web.project.GlobalRoleRelationService;
import jansible.web.project.RoleService;
import jansible.web.project.role.form.GlobalRoleRelationForm;
import jansible.web.project.role.form.GlobalRoleRelationTagUpdateForm;
import jansible.web.project.role.form.RoleForm;

import javax.servlet.http.HttpServletRequest;

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
	private RoleService roleService;
	@Autowired
	private GlobalRoleRelationService globalRoleRelationService;

	@RequestMapping("/project/viewRole")
	private String viewRole(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
		
		ProjectKey projectKey = new ProjectKey(projectName);
		
		// ロール
		model.addAttribute("roleForm", new RoleForm(projectKey));
		model.addAttribute("roleList", roleService.getRoleList(projectKey));
		model.addAttribute("roleKey", new RoleKey(projectKey));
		
		// global role
		model.addAttribute("globalRoleList", globalRoleRelationService.getGlobalRoleList());
		model.addAttribute("globalRoleRelationForm", new GlobalRoleRelationForm(projectKey));
		model.addAttribute("globalRoleRelationList", globalRoleRelationService.getGlobalRoleRelationViewList(projectKey));
		model.addAttribute("globalRoleRelationTagUpdateForm", new GlobalRoleRelationTagUpdateForm(projectKey));
				
		request.setAttribute("pageName", "project/project/role");
		return "common_frame";
	}

	@RequestMapping(value="/project/role/regist", method=RequestMethod.POST)
	private String registRole(@ModelAttribute RoleForm form, HttpServletRequest request){
		roleService.registRole(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/role/delete", method=RequestMethod.POST)
	private String deleteRole(@ModelAttribute RoleKey key, HttpServletRequest request){
		roleService.deleteRole(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/globalRole/regist", method=RequestMethod.POST)
	private String registGlobalRoleRelation(@ModelAttribute GlobalRoleRelationForm form, HttpServletRequest request) throws Exception{
		globalRoleRelationService.registGlobalRoleRelation(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/globalRole/update", method=RequestMethod.POST)
	private String updateGlobalRoleRelation(@ModelAttribute GlobalRoleRelationTagUpdateForm form, HttpServletRequest request) throws Exception{
		globalRoleRelationService.updateGlobalRoleRelation(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

}
