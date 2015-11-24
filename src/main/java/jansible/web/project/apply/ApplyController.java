package jansible.web.project.apply;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.web.project.GroupService;
import jansible.web.project.JenkinsBuildService;
import jansible.web.project.ProjectService;
import jansible.web.project.RoleService;
import jansible.web.project.ServerService;

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
	@Autowired
	private ServerService serverService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private RoleService roleService;

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
		
		model.addAttribute("buildForm", new BuildForm(serviceGroupKey));

		model.addAttribute("serverList", groupService.getServerRelationList(serviceGroupKey));
		model.addAttribute("roleList", groupService.getRoleRelationList(serviceGroupKey));
		
	    return "project/apply/group";
	}

	@RequestMapping("/applyServer/view")
	private String viewApply(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "environmentName", required = true) String environmentName,
			@RequestParam(value = "groupName", required = true) String groupName,
			@RequestParam(value = "serverName", required = true) String serverName,
			Model model){
		
		ServerRelationKey serverRelationKey = new ServerRelationKey();
		serverRelationKey.setProjectName(projectName);
		serverRelationKey.setEnvironmentName(environmentName);
		serverRelationKey.setGroupName(groupName);
		serverRelationKey.setServerName(serverName);
		
		model.addAttribute("buildForm", new ServerBuildForm(serverRelationKey));

		model.addAttribute("roleList", groupService.getRoleRelationList(serverRelationKey));
		
	    return "project/apply/server";
	}

	@RequestMapping(value="/project/jenkins/build", method=RequestMethod.POST)
	private String groupBuild(@ModelAttribute BuildForm form, HttpServletRequest request) throws Exception{
		jenkinsBuildService.groupBuild(form);
		
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
