package jansible.web.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
