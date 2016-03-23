package jansible.web.project.environment;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.web.project.EnvironmentService;
import jansible.web.project.project.EnvironmentForm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnvironmentController {
	@Autowired
	private EnvironmentService environmentService;

	@RequestMapping("/project/viewEnvironment")
	private String viewEnvironment(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
		
		ProjectKey projectKey = new ProjectKey(projectName);
		
		// 環境
		model.addAttribute("environmentForm", new EnvironmentForm(projectKey));
		model.addAttribute("environmentList", environmentService.getEnvironmentList(projectKey));
		model.addAttribute("environmentKey", new EnvironmentKey(projectKey));
		
		request.setAttribute("pageName", "project/project/environment");
		return "common_frame";
	}

	@RequestMapping(value="/project/environment/regist", method=RequestMethod.POST)
	private String registEnvironment(@ModelAttribute EnvironmentForm form, HttpServletRequest request){
		environmentService.registEnvironment(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/environment/delete", method=RequestMethod.POST)
	private String deleteEnvironment(@ModelAttribute EnvironmentKey key, HttpServletRequest request){
		environmentService.deleteEnvironment(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

}
