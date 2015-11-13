package jansible.web.project.top;

import javax.servlet.http.HttpServletRequest;

import jansible.web.project.ProjectService;

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
    private String top(Model model){
    	model.addAttribute("form", new ProjectForm());
    	model.addAttribute("projectList", projectService.getProjectList());
        return "project/top";
    }

    @RequestMapping(value="/project/regist", method=RequestMethod.POST)
	private String registProject(@ModelAttribute ProjectForm form, HttpServletRequest request) throws Exception{
		projectService.registProject(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
