package jansible.web.project.project;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;
import jansible.web.project.ApplyService;
import jansible.web.project.EnvironmentService;
import jansible.web.project.GlobalRoleRelationService;
import jansible.web.project.GroupService;
import jansible.web.project.ProjectService;
import jansible.web.project.RoleService;
import jansible.web.project.ServerService;
import jansible.web.project.group.ServiceGroupForm;
import jansible.web.project.server.ServerForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EnvironmentService environmentService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private ServerService serverService;
	@Autowired
	private GlobalRoleRelationService globalRoleRelationService;

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
    
    @RequestMapping("/project/viewRole")
	private String viewRole(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
    	
    	ProjectKey projectKey = new ProjectKey(projectName);
		
		// ロール
		model.addAttribute("roleForm", new RoleForm(projectKey));
		model.addAttribute("roleList", roleService.getRoleList(projectKey));
		model.addAttribute("roleKey", new RoleKey(projectKey));
		
		// global role
		model.addAttribute("globalRoleList", globalRoleRelationService.getGlobalRoleList());
		model.addAttribute("globalRoleRelationForm", new GlobalRoleRelationForm(projectKey));
		model.addAttribute("globalRoleRelationList", globalRoleRelationService.getGlobalRoleRelationViewList(projectKey));
		model.addAttribute("globalRoleRelationTagUpdateForm", new GlobalRoleRelationTagUpdateForm(projectKey));
				
		request.setAttribute("pageName", "project/project/role");
		return "common_frame";
	}
    
    @RequestMapping("/project/viewServer")
	private String viewServer(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
    	
    	ProjectKey projectKey = new ProjectKey(projectName);
    	
		model.addAttribute("environmentList", environmentService.getEnvironmentList(projectKey));
    	
		// サーバ関連
		model.addAttribute("serverForm", new ServerForm(projectKey));
		model.addAttribute("serverList", serverService.getServerList(projectKey));
		model.addAttribute("serverKey", new ServerKey(projectKey));
		
		request.setAttribute("pageName", "project/project/server");
		return "common_frame";
	}

	@RequestMapping("/project/viewGroup")
	private String viewGroup(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){

    	ProjectKey projectKey = new ProjectKey(projectName);
		
		// 環境リスト
		model.addAttribute("environmentList", environmentService.getEnvironmentList(projectKey));
		
		// グループ関連
		model.addAttribute("form", new ServiceGroupForm(projectKey));
		model.addAttribute("serviceGroupList", groupService.getServiceGroupList(projectKey));
		model.addAttribute("serviceGroupKey", new ServiceGroupKey(projectKey));
		
		request.setAttribute("pageName", "project/project/group");
		return "common_frame";
	}
}
