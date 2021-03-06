package jansible.web.project.apply.controller;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ProjectKey;
import jansible.web.UrlTemplateMapper;
import jansible.web.project.JenkinsBuildService;
import jansible.web.project.JenkinsResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultController {
	@Autowired
	private JenkinsResultService jenkinsResultService;
	@Autowired
	private JenkinsBuildService jenkinsBuildService;

	@RequestMapping(value="/project/jenkins/result")
	private String rebuild(
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "applyHistroyId", required = true) int applyHistroyId,
			Model model, HttpServletRequest request) throws Exception{
		
		ProjectKey projectKey = new ProjectKey(projectName);
		model.addAttribute("projectKey", projectKey);
		
		// 結果
		model.addAttribute("result", jenkinsResultService.getBuildResult(projectKey, applyHistroyId));
		
		// 再実行
		RebuildForm rebuildForm = new RebuildForm(projectKey);
		rebuildForm.setApplyHistroyId(applyHistroyId);
		model.addAttribute("rebuildForm", rebuildForm);
		
		request.setAttribute("pageName", UrlTemplateMapper.APPLY_RESULT.getTemplatePath());
		return "common_frame";
	}

	@RequestMapping(value="/project/jenkins/rebuild", method=RequestMethod.POST)
	private String rebuild(@ModelAttribute RebuildForm form, HttpServletRequest request) throws Exception{
		int applyHistroyId = jenkinsBuildService.rebuild(form);
		
		String url = "/project/jenkins/result";
		url = url + "?projectName=" + form.getProjectName();
		url = url + "&applyHistroyId=" + applyHistroyId;
		return "redirect:" + url;
	}
}
