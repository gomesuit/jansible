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

	@RequestMapping("/project/apply/view")
	private String viewApply(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "environmentName", required = true) String environmentName,
			@RequestParam(value = "groupName", required = true) String groupName,
			Model model,
			HttpServletRequest request){
		
		ServiceGroupKey serviceGroupKey = new ServiceGroupKey();
		serviceGroupKey.setProjectName(projectName);
		serviceGroupKey.setEnvironmentName(environmentName);
		serviceGroupKey.setGroupName(groupName);
		
		model.addAttribute("buildForm", new BuildForm(serviceGroupKey));

		model.addAttribute("serverList", groupService.getServerRelationList(serviceGroupKey));
		model.addAttribute("roleList", groupService.getRoleRelationList(serviceGroupKey));
		
		request.setAttribute("pageName", "project/apply/group");
		return "common_frame";
	}

	@RequestMapping("/project/applyServer/view")
	private String viewApply(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "environmentName", required = true) String environmentName,
			@RequestParam(value = "groupName", required = true) String groupName,
			@RequestParam(value = "serverName", required = true) String serverName,
			Model model,
			HttpServletRequest request){
		
		ServerRelationKey serverRelationKey = new ServerRelationKey();
		serverRelationKey.setProjectName(projectName);
		serverRelationKey.setEnvironmentName(environmentName);
		serverRelationKey.setGroupName(groupName);
		serverRelationKey.setServerName(serverName);
		
		model.addAttribute("buildForm", new ServerBuildForm(serverRelationKey));

		model.addAttribute("roleList", groupService.getRoleRelationList(serverRelationKey));
		
		request.setAttribute("pageName", "project/apply/server");
		return "common_frame";
	}

	@RequestMapping(value="/project/jenkins/build", method=RequestMethod.POST)
	private String groupBuild(@ModelAttribute BuildForm form, HttpServletRequest request) throws Exception{
		int applyHistroyId = jenkinsBuildService.groupBuild(form);
		
		String url = "/project/jenkins/result";
		url = url + "?projectName=" + form.getProjectName();
		url = url + "&applyHistroyId=" + applyHistroyId;
		return "redirect:" + url;
	}

	@RequestMapping(value="/project/jenkins/serverBuild", method=RequestMethod.POST)
	private String serverBuild(@ModelAttribute ServerBuildForm form, HttpServletRequest request) throws Exception{
		int applyHistroyId = jenkinsBuildService.buildforServer(form);
		
		String url = "/project/jenkins/result";
		url = url + "?projectName=" + form.getProjectName();
		url = url + "&applyHistroyId=" + applyHistroyId;
		return "redirect:" + url;
	}
}
