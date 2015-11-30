package jansible.web.project.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.Group;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbProject;
import jansible.model.database.DbServiceGroup;
import jansible.web.project.ApplyService;
import jansible.web.project.EnvironmentService;
import jansible.web.project.GitService;
import jansible.web.project.GlobalRoleRelationService;
import jansible.web.project.GroupService;
import jansible.web.project.TestService;
import jansible.web.project.JenkinsBuildService;
import jansible.web.project.ProjectService;
import jansible.web.project.RoleService;
import jansible.web.project.ServerService;
import jansible.web.project.server.ServerForm;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private JenkinsBuildService jenkinsBuildService;
	@Autowired
	private GitService gitService;
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
	
	@Autowired
	private TestService testService;
    
    @RequestMapping("/project/view")
	private String viewProject(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model){
    	
    	ProjectKey projectKey = new ProjectKey(projectName);
    	
    	// 環境
		model.addAttribute("environmentForm", new EnvironmentForm(projectKey));
		model.addAttribute("environmentList", environmentService.getEnvironmentList(projectKey));
		model.addAttribute("environmentKey", new EnvironmentKey(projectKey));
		
		// ロール
		model.addAttribute("roleForm", new RoleForm(projectKey));
		model.addAttribute("roleList", roleService.getRoleList(projectKey));
		model.addAttribute("roleKey", new RoleKey(projectKey));
		
		// Jenkins情報
		model.addAttribute("project", projectService.getProject(projectKey));
		DbProject dbProject = projectService.getProject(projectKey);
		JenkinsInfoForm jenkinsInfoForm = new JenkinsInfoForm(dbProject);
		jenkinsInfoForm.setJenkinsIpAddress(dbProject.getJenkinsIpAddress());
		jenkinsInfoForm.setJenkinsPort(dbProject.getJenkinsPort());
		jenkinsInfoForm.setJenkinsJobName(dbProject.getJenkinsJobName());
		model.addAttribute("jenkinsInfoForm", jenkinsInfoForm);
		
		// Git commit
		model.addAttribute("gitForm", new GitForm(projectKey));
		
		// 適用対象一覧
		model.addAttribute("groupList", getGroupList(projectKey));
		model.addAttribute("serverbuildList", groupService.getAllDbServerRelationList(projectKey));
		
		// 適用履歴
		model.addAttribute("applyHistoryList", applyService.getDbApplyHistoryList(projectKey));
		
		// サーバ関連
		model.addAttribute("serverForm", new ServerForm(projectKey));
		model.addAttribute("serverList", serverService.getServerList(projectKey));
		model.addAttribute("serverKey", new ServerKey(projectKey));
		
		// global role
		model.addAttribute("globalRoleList", globalRoleRelationService.getGlobalRoleList());
		model.addAttribute("globalRoleRelationForm", new GlobalRoleRelationForm(projectKey));
		model.addAttribute("globalRoleRelationList", globalRoleRelationService.getGlobalRoleRelationViewList(projectKey));
		model.addAttribute("globalRoleRelationTagUpdateForm", new GlobalRoleRelationTagUpdateForm(projectKey));
		
		// テストデータダウンロード
		ServerRelationKey serverRelationKey = new ServerRelationKey();
		serverRelationKey.setProjectName(projectName);
		model.addAttribute("serverRelationKey", serverRelationKey);
		
	    return "project/project/top";
	}

    @RequestMapping(value="/project/role/regist", method=RequestMethod.POST)
	private String registRole(@ModelAttribute RoleForm form, HttpServletRequest request){
    	roleService.registRole(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/role/delete", method=RequestMethod.POST)
	private String deleteRole(@ModelAttribute RoleKey key, HttpServletRequest request){
		roleService.deleteRole(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/environment/regist", method=RequestMethod.POST)
	private String registEnvironment(@ModelAttribute EnvironmentForm form, HttpServletRequest request){
		environmentService.registEnvironment(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/environment/delete", method=RequestMethod.POST)
	private String deleteEnvironment(@ModelAttribute EnvironmentKey key, HttpServletRequest request){
		environmentService.deleteEnvironment(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/git/commit", method=RequestMethod.POST)
	private String commitGit(@ModelAttribute GitForm form, HttpServletRequest request) throws Exception{
		gitService.commitGit(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/jenkins/regist", method=RequestMethod.POST)
	private String registJenkins(@ModelAttribute JenkinsInfoForm form, HttpServletRequest request){
		projectService.registJenkinsInfo(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/jenkins/rebuild", method=RequestMethod.POST)
	private String rebuild(@ModelAttribute RebuildForm form, HttpServletRequest request) throws Exception{
		jenkinsBuildService.rebuild(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private List<Group> getGroupList(ProjectKey projectKey) {
		List<Group> groupList = new ArrayList<>();
		
		List<DbEnvironment> dbEnvironmentList = environmentService.getEnvironmentList(projectKey);
		
		for(DbEnvironment dbEnvironment : dbEnvironmentList){
			List<DbServiceGroup> dbServiceGroupList = groupService.getServiceGroupList(dbEnvironment);
			for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
				Group group = new Group(dbServiceGroup.getEnvironmentName(), dbServiceGroup.getGroupName());
				groupList.add(group);
			}
		}
		
		return groupList;
	}

    @RequestMapping(value="/project/globalRole/regist", method=RequestMethod.POST)
	private String registGlobalRoleRelation(@ModelAttribute GlobalRoleRelationForm form, HttpServletRequest request){
    	globalRoleRelationService.registGlobalRoleRelation(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/globalRole/update", method=RequestMethod.POST)
	private String deleteGlobalRoleRelation(@ModelAttribute GlobalRoleRelationTagUpdateForm form, HttpServletRequest request){
		globalRoleRelationService.updateGlobalRoleRelation(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	

    @RequestMapping("/project/testZip")
    @ResponseBody
	private Resource testZip(@ModelAttribute ServerRelationKey key, HttpServletResponse response, HttpServletRequest request) throws Exception{
    	File zipfile = testService.getTestZipFile(key);
    	
		response.setHeader("Content-Disposition","attachment; filename=\"" + zipfile.getName() +"\"");
		
		request.setAttribute("tempDir", zipfile.getParent());
		
    	return new FileSystemResource(zipfile);
    }
    
    @Bean
    public MappedInterceptor interceptor() {
        return new MappedInterceptor(new String[]{"/project/testZip"}, new DeleteTempDirInterceptor());
    }
    
    private class DeleteTempDirInterceptor implements HandlerInterceptor{

		@Override
		public boolean preHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler) throws Exception {
			return true;
		}

		@Override
		public void postHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {
		}

		@Override
		public void afterCompletion(HttpServletRequest request,
				HttpServletResponse response, Object handler, Exception ex)
				throws Exception {
			String tempDir = (String) request.getAttribute("tempDir");
			File dir = new File(tempDir);
			FileUtils.deleteDirectory(dir);
		}
    	
    }
}
