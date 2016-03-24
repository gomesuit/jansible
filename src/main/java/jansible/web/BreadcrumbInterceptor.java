package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BreadcrumbInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
		
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		String breadcrumbActive = null;
		
		String projectName = (String)request.getParameter("projectName");
		if(projectName != null){
			param.put("projectName", projectName);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/view", param), "Project", false));
			breadcrumbActive = projectName;
		}
		
		String environmentName = (String)request.getParameter("environmentName");
		if(environmentName != null){
			param.put("environmentName", environmentName);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/environment/view", param), "Environment", false));
			breadcrumbActive = environmentName;
		}
		
		String groupName = (String)request.getParameter("groupName");
		if(groupName != null){
			param.put("groupName", groupName);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/serviceGroup/view", param), "Group", false));
			breadcrumbActive = groupName;
		}
		
		String serverName = (String)request.getParameter("serverName");
		if(serverName != null){
			param.put("serverName", serverName);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/server/view", param), "Server", false));
			breadcrumbActive = serverName;
		}
		
		String roleName = (String)request.getParameter("roleName");
		if(roleName != null){
			param.put("roleName", roleName);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/role/view", param), "Role", false));
			breadcrumbActive = roleName;
		}
		
		String taskId = (String)request.getParameter("taskId");
		if(taskId != null){
			param.put("taskId", taskId);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/task/view", param), "Task", false));
			breadcrumbActive = taskId;
		}
		
		String applyHistroyId = (String)request.getParameter("applyHistroyId");
		if(applyHistroyId != null){
			param.put("applyHistroyId", applyHistroyId);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/jenkins/result", param), "Result", false));
			breadcrumbActive = applyHistroyId;
		}
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		request.setAttribute("breadcrumbActive", breadcrumbActive);
	}
	
	private String getUrl(String baseUrl, Map<String, String> param){
		if(param == null) return baseUrl;
		if(param.isEmpty()) return baseUrl;
		
		String url = baseUrl + "?";
		for(Entry<String, String> keyValue : param.entrySet()){
			url = url + keyValue.getKey() + "=" + keyValue.getValue() + "&";
		}
		
		return url;
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

}