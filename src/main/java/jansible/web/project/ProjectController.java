package jansible.web.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.EnvironmentKey;
import jansible.model.common.Group;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbProject;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskDetail;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.model.yamldump.YamlModule;
import jansible.util.YamlDumper;
import jansible.web.module.ModuleService;
import jansible.web.project.environment.EnvironmentForm;
import jansible.web.project.project.BuildForm;
import jansible.web.project.project.GitForm;
import jansible.web.project.project.JenkinsInfoForm;
import jansible.web.project.project.ProjectForm;
import jansible.web.project.project.RebuildForm;
import jansible.web.project.role.GeneralFileForm;
import jansible.web.project.role.RoleForm;
import jansible.web.project.role.RoleVariableForm;
import jansible.web.project.role.UploadForm;
import jansible.web.project.task.TaskDetailForm;
import jansible.web.project.task.TaskForm;
import jansible.web.project.task.TaskParameter;
import jansible.web.project.task.TaskView;

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

	@RequestMapping(value="/project/jenkins/build", method=RequestMethod.POST)
	private String build(@ModelAttribute BuildForm form, HttpServletRequest request) throws Exception{
		projectService.build(form);
		
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

	@RequestMapping("/role/view")
	private String viewRole(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "roleName", required = true) String roleName,
			Model model){
		RoleKey roleKey = new RoleKey();
		roleKey.setProjectName(projectName);
		roleKey.setRoleName(roleName);
		
		TaskForm form = new TaskForm(roleKey);
		
		model.addAttribute("form", form);
		List<DbTask> dbTaskList = projectService.getTaskList(roleKey);
		List<TaskView> taskViewList = createTaskViewList(dbTaskList);
		model.addAttribute("taskList", taskViewList);
		
		TaskKey taskKey = new TaskKey(roleKey);
		model.addAttribute("taskKey", taskKey);
		
		// module名リスト
		model.addAttribute("moduleNameList", moduleService.getModuleNameList());
		
		UploadForm uploadForm = new UploadForm(roleKey);
		model.addAttribute("uploadForm", uploadForm);
	
		model.addAttribute("templateList", projectService.getDbTemplateList(roleKey));
		model.addAttribute("fileList", projectService.getDbFileList(roleKey));
		
		GeneralFileForm fileForm = new GeneralFileForm(roleKey);
		model.addAttribute("fileForm", fileForm);
		
		RoleVariableForm roleVariableForm = new RoleVariableForm(roleKey);
		model.addAttribute("variableForm", roleVariableForm);
	
		RoleVariableKey roleVariableKey = new RoleVariableKey(roleKey);
		model.addAttribute("roleVariableKey", roleVariableKey);
		
		model.addAttribute("variableList", projectService.getDbRoleVariableList(roleKey));
		
	    return "project/role/top";
	}

	@RequestMapping(value="/project/roleVariable/regist", method=RequestMethod.POST)
	private String registRoleVariable(@ModelAttribute RoleVariableForm form, HttpServletRequest request){
		projectService.registRoleVariable(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/roleVariable/delete", method=RequestMethod.POST)
	private String deleteRoleVariable(@ModelAttribute RoleVariableKey key, HttpServletRequest request){
		projectService.deleteRoleVariable(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	private TaskView createTaskView(DbTask dbTask) {
		TaskView taskView = new TaskView();
		taskView.setTaskId(dbTask.getTaskId());
		taskView.setModuleName(dbTask.getModuleName());
		taskView.setDescription(dbTask.getDescription());
		List<DbTaskDetail> dbTaskDetailList = projectService.getTaskDetailList(dbTask);
		YamlModule yamlModule = new YamlModule(dbTask.getModuleName(), projectService.createParameters(dbTaskDetailList));
		yamlModule.setDescription(dbTask.getDescription());
		taskView.setParametersValue(yamlModule.getParameters().toString());
		return taskView;
	}

	private List<TaskView> createTaskViewList(List<DbTask> dbTaskList){
		List<TaskView> taskViewList = new ArrayList<>();
		for(DbTask dbTask : dbTaskList){
			TaskView taskView = createTaskView(dbTask);
			taskViewList.add(taskView);
		}
		return taskViewList;
	}

	@RequestMapping(value="/project/task/regist", method=RequestMethod.POST)
	private String registTask(@ModelAttribute TaskForm form, HttpServletRequest request){
		projectService.registTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value="/project/task/delete", method=RequestMethod.POST)
	private String deleteTask(@ModelAttribute TaskKey key, HttpServletRequest request){
		projectService.deleteTask(key);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping("/task/view")
	private String viewTask(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "roleName", required = true) String roleName,
			@RequestParam(value = "taskId", required = true) int taskId,
			Model model){
		TaskKey taskKey = new TaskKey();
		taskKey.setProjectName(projectName);
		taskKey.setRoleName(roleName);
		taskKey.setTaskId(taskId);
		
		DbTask dbTask = projectService.getTask(taskKey);
		String moduleName = dbTask.getModuleName();
		model.addAttribute("moduleName", moduleName);
		
		HtmlModule module = moduleService.getModule(moduleName);
	
		TaskDetailForm form = new TaskDetailForm(taskKey);
		form.setDescription(dbTask.getDescription());
		List<TaskParameter> taskParameterList = createBlankTaskParameterList(module);
		List<DbTaskDetail> dbTaskDetailList = projectService.getTaskDetailList(taskKey);
		mergeParameterList(taskParameterList, dbTaskDetailList);
		form.setTaskParameterList(taskParameterList);
		model.addAttribute("form", form);
		
		model.addAttribute("variableList", projectService.getDbRoleVariableList(taskKey));
		
	
		List<DbTask> dbTaskList = new ArrayList<>();
		dbTaskList.add(dbTask);
		List<YamlModule> modules = projectService.createYamlModuleList(dbTaskList);
		model.addAttribute("yaskYaml", yamlDumper.dump(modules).replaceAll("\n", "<br />"));
		
	    return "project/task/top";
	}

	private void mergeParameterList(List<TaskParameter> taskParameterList, List<DbTaskDetail> dbTaskDetailList) {
		for(TaskParameter taskParameter : taskParameterList){
			String formParameterName = taskParameter.getParameterName();
			for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
				String dbParameterName = dbTaskDetail.getParameterName();
				if(formParameterName.equals(dbParameterName)){
					taskParameter.setParameterValue(dbTaskDetail.getParameterValue());
				}
			}
		}
	}

	private List<TaskParameter> createBlankTaskParameterList(HtmlModule module) {
		List<TaskParameter> taskParameterList = new ArrayList<>();
		for(HtmlParameter htmlParameter : module.getParameterList()){
			TaskParameter taskParameter = new TaskParameter();
			taskParameter.setParameterName(htmlParameter.getName());
			taskParameter.setAddedVersion(htmlParameter.getAddedVersion());
			taskParameter.setRequired(htmlParameter.isRequired());
			taskParameter.setDefaultValue(htmlParameter.getDefaultValue());
			taskParameter.setChoices(htmlParameter.getChoices());
			taskParameter.setDescription(htmlParameter.getDescription());
			taskParameterList.add(taskParameter);
		}
		return taskParameterList;
	}

	@RequestMapping(value="/project/taskdetail/regist", method=RequestMethod.POST)
	private String registTaskDetail(@ModelAttribute TaskDetailForm form, HttpServletRequest request){
		projectService.updateTask(form);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

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
				
		BuildForm buildForm = new BuildForm(serviceGroupKey);
		model.addAttribute("buildForm", buildForm);
		
		List<Group> groupList = getGroupList(serviceGroupKey);
		model.addAttribute("groupList", groupList);
		
	    return "project/apply/top";
	}
}
