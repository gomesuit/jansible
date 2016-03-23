package jansible.web.project.apply.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jansible.model.common.Group;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbServiceGroup;
import jansible.web.project.ApplyService;
import jansible.web.project.EnvironmentService;
import jansible.web.project.GroupService;
import jansible.web.project.ProjectService;
import jansible.web.project.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Controller
public class ApplyController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EnvironmentService environmentService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private TestService testService;

	@RequestMapping("/project/viewApply")
	private String viewApply(
			@RequestParam(value = "projectName", required = true) String projectName,
			Model model,
			HttpServletRequest request){
		
		ProjectKey projectKey = new ProjectKey(projectName);
		
		model.addAttribute("project", projectService.getProject(projectKey));
		
		// 適用対象一覧
		model.addAttribute("groupList", getGroupList(projectKey));
		model.addAttribute("serverbuildList", groupService.getAllDbServerRelationList(projectKey));
		
		// 適用履歴
		model.addAttribute("applyHistoryList", applyService.getDbApplyHistoryList(projectKey));
		
		// テストデータダウンロード
		model.addAttribute("serverRelationKey", new ServerRelationKey(projectKey));
		
		request.setAttribute("pageName", "project/project/apply");
		return "common_frame";
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

	@RequestMapping("/project/testZip")
	@ResponseBody
	private Resource downloadTestZip(@ModelAttribute ServerRelationKey key, HttpServletResponse response, HttpServletRequest request) throws Exception{
		File zipfile = testService.getTestZipFile(key);
		
		response.setHeader("Content-Disposition","attachment; filename=\"" + zipfile.getName() +"\"");
		
		request.setAttribute("tempDir", zipfile.getParent());
		
		return new FileSystemResource(zipfile);
	}

	@Bean
	public MappedInterceptor interceptor() {
	    return new MappedInterceptor(new String[]{"/project/testZip"}, (HandlerInterceptor) new DeleteTempDirInterceptor());
	}

	private class DeleteTempDirInterceptor implements HandlerInterceptor {

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
