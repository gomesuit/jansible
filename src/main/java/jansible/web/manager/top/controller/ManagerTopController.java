package jansible.web.manager.top.controller;

import jansible.web.UrlTemplateMapper;
import jansible.web.manager.GlobalRoleService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManagerTopController {
	@Autowired
	private GlobalRoleService globalRoleService;

	@RequestMapping("/manager")
	private String top(Model model, HttpServletRequest request){
		
		request.setAttribute("pageName", UrlTemplateMapper.MANAGER_TOP.getTemplatePath());
		return "common_frame";
	}
}
