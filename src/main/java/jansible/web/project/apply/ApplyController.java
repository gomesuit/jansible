package jansible.web.project.apply;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ServiceGroupKey;
import jansible.web.project.JenkinsBuildService;
import jansible.web.project.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplyController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private JenkinsBuildService jenkinsBuildService;

	@RequestMapping("/apply/view")
	private String viewApply(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "environmentName", required = true) String environmentName,
			@RequestParam(value = "groupName", required = true) String groupName,
			Model model){
		
		ServiceGroupKey serviceGroupKey = new ServiceGroupKey();
		serviceGroupKey.setProjectName(projectName);
		serviceGroupKey.setEnvironmentName(environmentName);
		serviceGroupKey.setGroupName(groupName);
		
		BuildForm buildForm = new BuildForm(serviceGroupKey);
		model.addAttribute("buildForm", buildForm);
		
	    return "project/apply/group";
	}

	@RequestMapping("/applyServer/view")
	private String viewApply(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "environmentName", required = true) String environmentName,
			@RequestParam(value = "groupName", required = true) String groupName,
			@RequestParam(value = "serverName", required = true) String serverName,
			Model model){
		
		ServerBuildForm buildForm = new ServerBuildForm();
		buildForm.setProjectName(projectName);
		buildForm.setEnvironmentName(environmentName);
		buildForm.setGroupName(groupName);
		buildForm.setServerName(serverName);
		model.addAttribute("buildForm", buildForm);
		
	    return "project/apply/server";
	}

	@RequestMapping(value="/project/jenkins/build", method=RequestMethod.POST)
	private String build(@ModelAttribute BuildForm form, HttpServletRequest request) throws Exception{
		jenkinsBuildService.build(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/jenkins/serverBuild", method=RequestMethod.POST)
	private String serverBuild(@ModelAttribute ServerBuildForm form, HttpServletRequest request) throws Exception{
		jenkinsBuildService.buildforServer(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
