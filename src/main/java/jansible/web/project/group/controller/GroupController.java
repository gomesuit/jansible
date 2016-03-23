package jansible.web.project.group.controller;

import jansible.model.common.ProjectKey;
import jansible.model.common.ServiceGroupKey;
import jansible.web.project.EnvironmentService;
import jansible.web.project.GroupService;
import jansible.web.project.group.form.ServiceGroupForm;

import javax.servlet.http.HttpServletRequest;

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
	private EnvironmentService environmentService;
	@Autowired
	private GroupService groupService;

	@RequestMapping("/project/viewGroup")
	private String viewGroup(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
	
		ProjectKey projectKey = new ProjectKey(projectName);
		
		// 環境リスト
		model.addAttribute("environmentList", environmentService.getEnvironmentList(projectKey));
		
		// グループ関連
		model.addAttribute("form", new ServiceGroupForm(projectKey));
		model.addAttribute("serviceGroupList", groupService.getServiceGroupList(projectKey));
		model.addAttribute("serviceGroupKey", new ServiceGroupKey(projectKey));
		
		request.setAttribute("pageName", "project/project/group");
		return "common_frame";
	}

	@RequestMapping(value="/project/group/regist", method=RequestMethod.POST)
	private String registServiceGroup(@ModelAttribute ServiceGroupForm form, HttpServletRequest request){
		groupService.registServiceGroup(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/serviceGroup/delete", method=RequestMethod.POST)
	private String deleteServiceGroup(@ModelAttribute ServiceGroupKey key, HttpServletRequest request){
		groupService.deleteServiceGroup(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
