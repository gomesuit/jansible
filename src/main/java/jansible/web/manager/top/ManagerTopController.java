package jansible.web.manager.top;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.GlobalRoleKey;
import jansible.web.manager.GlobalRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManagerTopController {
	@Autowired
	private GlobalRoleService globalRoleService;
    
    @RequestMapping("/manager")
    private String top(Model model){
    	model.addAttribute("form", new GlobalRoleForm());
    	model.addAttribute("roleList", globalRoleService.getRoleList());
    	model.addAttribute("roleKey", new GlobalRoleKey());
        return "manager/top";
    }

    @RequestMapping(value="/manager/role/regist", method=RequestMethod.POST)
	private String registProject(@ModelAttribute GlobalRoleForm form, HttpServletRequest request) throws Exception{
    	globalRoleService.registRole(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

    @RequestMapping(value="/manager/role/delete", method=RequestMethod.POST)
	private String deleteProject(@ModelAttribute GlobalRoleKey key, HttpServletRequest request) throws Exception{
    	globalRoleService.deleteRole(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
