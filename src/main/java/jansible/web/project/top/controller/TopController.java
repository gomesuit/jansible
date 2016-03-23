package jansible.web.project.top.controller;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ProjectKey;
import jansible.web.project.ProjectService;
import jansible.web.project.top.form.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TopController {
	@Autowired
	private ProjectService projectService;
    
    @RequestMapping("/")
    private String top(Model model, HttpServletRequest request){
    	model.addAttribute("form", new ProjectForm());
    	model.addAttribute("projectList", projectService.getProjectList());
    	model.addAttribute("projectKey", new ProjectKey());

		request.setAttribute("pageName", "project/top");
		return "common_frame";
    }

    @RequestMapping(value="/project/regist", method=RequestMethod.POST)
	private String registProject(@ModelAttribute ProjectForm form, HttpServletRequest request) throws Exception{
		projectService.registProject(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

    @RequestMapping(value="/project/delete", method=RequestMethod.POST)
	private String deleteProject(@ModelAttribute ProjectKey key, HttpServletRequest request) throws Exception{
		projectService.deleteProject(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
