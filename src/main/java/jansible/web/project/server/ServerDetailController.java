package jansible.web.project.server;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ServerKey;
import jansible.model.common.ServerParameterKey;
import jansible.model.common.ServerVariableKey;
import jansible.web.project.GroupService;
import jansible.web.project.ServerService;
import jansible.web.project.VariableService;
import jansible.web.project.server.ServerVariableForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ServerDetailController {
	@Autowired
	private VariableService variableService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ServerService serverService;
    
    @RequestMapping("/project/server/view")
	private String viewServer(
    		@RequestParam(value = "projectName", required = true) String projectName,
    		@RequestParam(value = "serverName", required = true) String serverName,
			Model model, HttpServletRequest request) {
    	
    	ServerKey serverKey = new ServerKey();
    	serverKey.setProjectName(projectName);
    	serverKey.setServerName(serverName);
    	
    	// サーバパラメータ関連
		model.addAttribute("serverParameterForm", new ServerParameterForm(serverKey));
		model.addAttribute("serverParameterList", serverService.getServerParameterList(serverKey));
		model.addAttribute("serverParameterKey", new ServerParameterKey(serverKey));
		
		// 変数関連
		model.addAttribute("variableForm", new ServerVariableForm(serverKey));		
		model.addAttribute("allVariableNameList", variableService.getAllDbVariableNameList(serverKey));
		model.addAttribute("variableList", variableService.getDbServerVariableList(serverKey));
		model.addAttribute("serverVariableKey", new ServerVariableKey(serverKey));
		
		request.setAttribute("pageName", "project/server/top");
		return "common_frame";
	}

	@RequestMapping(value="/project/serverVariable/regist", method=RequestMethod.POST)
    private String registServerVariable(@ModelAttribute ServerVariableForm form, HttpServletRequest request){
    	variableService.registServerVariable(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/serverVariable/delete", method=RequestMethod.POST)
    private String deleteServerVariable(@ModelAttribute ServerVariableKey key, HttpServletRequest request){
    	variableService.deleteServerVariable(key);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

	@RequestMapping(value="/project/serverParameter/regist", method=RequestMethod.POST)
    private String registServerParameter(@ModelAttribute ServerParameterForm form, HttpServletRequest request){
		serverService.registServerParameter(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }

    @RequestMapping(value="/project/serverParameter/delete", method=RequestMethod.POST)
    private String deleteServerParameter(@ModelAttribute ServerParameterKey key, HttpServletRequest request){
    	serverService.deleteServerParameter(key);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
