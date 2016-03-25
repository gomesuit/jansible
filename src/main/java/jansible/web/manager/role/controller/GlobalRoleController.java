package jansible.web.manager.role.controller;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.GlobalRoleKey;
import jansible.web.UrlTemplateMapper;
import jansible.web.manager.GlobalRoleService;
import jansible.web.manager.top.form.GlobalRoleForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GlobalRoleController {
	@Autowired
	private GlobalRoleService globalRoleService;
	
	private static final String GLOBAL_ROLE_PREFIX = "GLOBAL_";

	@RequestMapping("/manager/role")
	private String top(Model model, HttpServletRequest request){
		model.addAttribute("form", new GlobalRoleForm());
		model.addAttribute("roleList", globalRoleService.getRoleList());
		model.addAttribute("roleKey", new GlobalRoleKey());
		
		request.setAttribute("pageName", UrlTemplateMapper.MANAGER_ROLE.getTemplatePath());
		return "common_frame";
	}

	@RequestMapping(value="/manager/role/regist", method=RequestMethod.POST)
	private String registProject(@ModelAttribute GlobalRoleForm form, HttpServletRequest request) throws Exception{
		// ロール名に接頭辞を付与する
		String roleName = GLOBAL_ROLE_PREFIX + form.getRoleName();
		form.setRoleName(roleName);
		
		globalRoleService.registRole(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/manager/role/delete", method=RequestMethod.POST)
	private String deleteProject(@ModelAttribute GlobalRoleKey key, HttpServletRequest request) throws Exception{
		globalRoleService.deleteRole(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
