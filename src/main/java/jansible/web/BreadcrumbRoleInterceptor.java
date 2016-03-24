package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class BreadcrumbRoleInterceptor extends BreadcrumbBaseInterceptor{

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
		
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		String breadcrumbActive = null;
		
		String projectName = (String)request.getParameter("projectName");
		if(projectName != null){
			param.put("projectName", projectName);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/role", param), "Role", false));
			breadcrumbActive = projectName;
		}
		
		String roleName = (String)request.getParameter("roleName");
		if(roleName != null){
			param.put("roleName", roleName);
			breadcrumbActive = roleName;
		}
		
		String taskId = (String)request.getParameter("taskId");
		if(taskId != null){
			param.put("taskId", taskId);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/task/view", param), "Task", false));
			breadcrumbActive = taskId;
		}
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		request.setAttribute("breadcrumbActive", breadcrumbActive);
		
	}

}
