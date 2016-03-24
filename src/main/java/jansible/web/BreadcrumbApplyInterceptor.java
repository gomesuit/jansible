package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class BreadcrumbApplyInterceptor extends BreadcrumbBaseInterceptor{

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
		
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		String breadcrumbActive = null;
		
		String projectName = (String)request.getParameter("projectName");
		param.put("projectName", projectName);
		breadcrumbList.add(new Breadcrumb(getUrl("/project/apply", param), "Apply", false));
		
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
		
		String applyHistroyId = (String)request.getParameter("applyHistroyId");
		if(applyHistroyId != null){
			param.put("applyHistroyId", applyHistroyId);
			breadcrumbList.add(new Breadcrumb(getUrl("/project/jenkins/result", param), "Result", false));
			breadcrumbActive = applyHistroyId;
		}
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		request.setAttribute("breadcrumbActive", breadcrumbActive);
		
	}

}
