package jansible.web.project;

import javax.servlet.http.HttpServletRequest;

import jansible.web.project.form.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;

    @RequestMapping("/project/test")
    @ResponseBody
    private String test(){
        return "Hello World!";
    }
    
    @RequestMapping("/project/create")
    private String registProject(Model model){
    	model.addAttribute("form", new ProjectForm());
    	model.addAttribute("projectList", projectService.getProjectList());
        return "project/sample";
    }

    @RequestMapping(value="/project/regist", method=RequestMethod.POST)
    private String registProject(@ModelAttribute ProjectForm form, HttpServletRequest request){
    	projectService.registProject(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
