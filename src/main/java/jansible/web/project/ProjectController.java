package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.Group;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbProject;
import jansible.model.database.DbServiceGroup;
import jansible.util.YamlDumper;
import jansible.web.module.ModuleService;
import jansible.web.project.environment.EnvironmentForm;
import jansible.web.project.project.GitForm;
import jansible.web.project.project.JenkinsInfoForm;
import jansible.web.project.project.ProjectForm;
import jansible.web.project.project.RebuildForm;
import jansible.web.project.role.RoleForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private YamlDumper yamlDumper;
    
    @RequestMapping("/")
    private String top(Model model){
    	model.addAttribute("form", new ProjectForm());
    	model.addAttribute("projectList", projectService.getProjectList());
        return "project/top";
    }

    @RequestMapping(value="/project/regist", method=RequestMethod.POST)
	private String registProject(@ModelAttribute ProjectForm form, HttpServletRequest request) throws Exception{
		projectService.registProject(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping("/project/view")
	private String viewProject(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model){
    	
    	ProjectKey projectKey = new ProjectKey(projectName);
    	
		EnvironmentForm environmentForm = new EnvironmentForm(projectKey);
		model.addAttribute("environmentForm", environmentForm);
		model.addAttribute("environmentList", projectService.getEnvironmentList(projectKey));
		
		EnvironmentKey environmentKey = new EnvironmentKey(projectKey);
		model.addAttribute("environmentKey", environmentKey);
		
		RoleForm roleForm = new RoleForm(projectKey);
		model.addAttribute("roleForm", roleForm);
		model.addAttribute("roleList", projectService.getRoleList(projectKey));
	
		RoleKey roleKey = new RoleKey(projectKey);
		model.addAttribute("roleKey", roleKey);

		model.addAttribute("project", projectService.getProject(projectKey));
		
		GitForm gitForm = new GitForm(projectKey);
		model.addAttribute("gitForm", gitForm);
		
		List<Group> groupList = getGroupList(projectKey);
		model.addAttribute("groupList", groupList);
		
		DbProject dbProject = projectService.getProject(projectKey);
		JenkinsInfoForm jenkinsInfoForm = new JenkinsInfoForm(dbProject);
		jenkinsInfoForm.setJenkinsIpAddress(dbProject.getJenkinsIpAddress());
		jenkinsInfoForm.setJenkinsPort(dbProject.getJenkinsPort());
		jenkinsInfoForm.setJenkinsJobName(dbProject.getJenkinsJobName());
		model.addAttribute("jenkinsInfoForm", jenkinsInfoForm);

		model.addAttribute("applyHistoryList", projectService.getDbApplyHistoryList(projectKey));
		
		RebuildForm rebuildForm = new RebuildForm(projectKey);
		model.addAttribute("rebuildForm", rebuildForm);
		
	    return "project/project/top";
	}

    @RequestMapping(value="/project/role/regist", method=RequestMethod.POST)
	private String registRole(@ModelAttribute RoleForm form, HttpServletRequest request){
		projectService.registRole(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/role/delete", method=RequestMethod.POST)
	private String deleteRole(@ModelAttribute RoleKey key, HttpServletRequest request){
		projectService.deleteRole(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/environment/regist", method=RequestMethod.POST)
	private String registEnvironment(@ModelAttribute EnvironmentForm form, HttpServletRequest request){
		projectService.registEnvironment(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/environment/delete", method=RequestMethod.POST)
	private String deleteEnvironment(@ModelAttribute EnvironmentKey key, HttpServletRequest request){
		projectService.deleteEnvironment(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/git/commit", method=RequestMethod.POST)
	private String commitGit(@ModelAttribute GitForm form, HttpServletRequest request) throws Exception{
		projectService.commitGit(form);
		
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
		projectService.rebuild(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private List<Group> getGroupList(ProjectKey projectKey) {
		List<Group> groupList = new ArrayList<>();
		
		List<DbEnvironment> dbEnvironmentList = projectService.getEnvironmentList(projectKey);
		
		for(DbEnvironment dbEnvironment : dbEnvironmentList){
			List<DbServiceGroup> dbServiceGroupList = projectService.getServiceGroupList(dbEnvironment);
			for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
				Group group = new Group(dbServiceGroup.getEnvironmentName(), dbServiceGroup.getGroupName());
				groupList.add(group);
			}
		}
		
		return groupList;
	}
}
