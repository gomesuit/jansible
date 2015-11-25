package jansible.web.manager.top;

import jansible.web.project.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopController {
	@Autowired
	private ProjectService projectService;
    
    @RequestMapping("/manager/top")
    private String top(Model model){
        return "project/top";
    }
}
