package jansible.web.project.apply;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbProject;
import jansible.web.project.ApplyService;
import jansible.web.project.GitService;
import jansible.web.project.GroupService;
import jansible.web.project.JenkinsBuildService;
import jansible.web.project.ProjectService;
import jansible.web.project.project.GitConpareForm;
import jansible.web.project.project.GitForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplyDetailController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private JenkinsBuildService jenkinsBuildService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private GitService gitService;

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

		model.addAttribute("serverList", groupService.getServerRelationList(serviceGroupKey));
		model.addAttribute("roleList", groupService.getRoleRelationList(serviceGroupKey));
		
		// Git commit
		model.addAttribute("gitForm", new GitForm(serviceGroupKey));

		// Git compare
		model.addAttribute("gitConpareForm", new GitConpareForm(serviceGroupKey));
		model.addAttribute("tagNameList", applyService.getTagNameList(serviceGroupKey));
		
		// タグ＆適用
		model.addAttribute("buildForm", new BuildForm(serviceGroupKey));

		// 適用履歴
		model.addAttribute("applyHistoryList", applyService.getDbApplyHistoryListByGroup(serviceGroupKey));
		
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
		
		model.addAttribute("roleList", groupService.getRoleRelationList(serverRelationKey));
		
		// Git commit
		model.addAttribute("gitForm", new GitForm(serverRelationKey));

		// Git compare
		model.addAttribute("gitConpareForm", new GitConpareForm(serverRelationKey));
		model.addAttribute("tagNameList", applyService.getTagNameList(serverRelationKey));

		// タグ＆適用
		model.addAttribute("buildForm", new ServerBuildForm(serverRelationKey));

		// 適用履歴
		model.addAttribute("applyHistoryList", applyService.getDbApplyHistoryListByServer(serverRelationKey));
		
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

	@RequestMapping(value="/project/git/commit", method=RequestMethod.POST)
	private String commitGit(@ModelAttribute GitForm form, HttpServletRequest request) throws Exception{
		gitService.commitGit(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/git/compare", method=RequestMethod.POST)
	private String compareGit(@ModelAttribute GitConpareForm form, HttpServletRequest request) throws Exception{		
		DbProject project = projectService.getProject(form);
		String gitUrl = project.getRepositoryUrl();
		String compareUrl = gitService.getConpareUrl(gitUrl, form);
		
		return "redirect:" + compareUrl;
	}
}
