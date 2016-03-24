package jansible.web.project.server.controller;

import jansible.model.common.ProjectKey;
import jansible.model.common.ServerKey;
import jansible.web.project.EnvironmentService;
import jansible.web.project.ServerService;
import jansible.web.project.server.form.ServerForm;

import javax.servlet.http.HttpServletRequest;

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
	private EnvironmentService environmentService;
	@Autowired
	private ServerService serverService;

	@RequestMapping("/project/server")
	private String viewServer(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
		
		ProjectKey projectKey = new ProjectKey(projectName);
		
		model.addAttribute("environmentList", environmentService.getEnvironmentList(projectKey));
		
		// サーバ関連
		model.addAttribute("serverForm", new ServerForm(projectKey));
		model.addAttribute("serverList", serverService.getServerList(projectKey));
		model.addAttribute("serverKey", new ServerKey(projectKey));
		
		request.setAttribute("pageName", "project/project/server");
		return "common_frame";
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

}
