package jansible.web.project.project.controller;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ProjectKey;
import jansible.model.database.DbProject;
import jansible.web.UrlTemplateMapper;
import jansible.web.project.ProjectService;
import jansible.web.project.project.form.JenkinsInfoForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
    
    @RequestMapping("/project/view")
	private String viewProject(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
    	
    	ProjectKey projectKey = new ProjectKey(projectName);
		
		// Jenkins情報
		model.addAttribute("project", projectService.getProject(projectKey));
		model.addAttribute("jenkinsInfoForm", createJenkinsInfoForm(projectKey));
		
		request.setAttribute("pageName", UrlTemplateMapper.PROJECT.getTemplatePath());
		return "common_frame";
	}
	
    private JenkinsInfoForm createJenkinsInfoForm(ProjectKey key){
		DbProject dbProject = projectService.getProject(key);
    	JenkinsInfoForm jenkinsInfoForm = new JenkinsInfoForm(dbProject);
		jenkinsInfoForm.setJenkinsIpAddress(dbProject.getJenkinsIpAddress());
		jenkinsInfoForm.setJenkinsPort(dbProject.getJenkinsPort());
		jenkinsInfoForm.setJenkinsJobName(dbProject.getJenkinsJobName());
		
		return jenkinsInfoForm;
	}

    @RequestMapping(value="/project/jenkins/regist", method=RequestMethod.POST)
	private String registJenkins(@ModelAttribute JenkinsInfoForm form, HttpServletRequest request){
		projectService.registJenkinsInfo(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
