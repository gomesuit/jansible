package jansible.web.project.result;

import javax.servlet.http.HttpServletRequest;

import jansible.model.common.ProjectKey;
import jansible.web.project.JenkinsResultService;
import jansible.web.project.project.RebuildForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultController {
	@Autowired
	private JenkinsResultService jenkinsResultService;

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
		
		request.setAttribute("pageName", "project/result/top");
		return "common_frame";
	}
}
