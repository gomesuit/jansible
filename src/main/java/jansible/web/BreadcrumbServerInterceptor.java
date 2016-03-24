package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class BreadcrumbServerInterceptor extends BreadcrumbBaseInterceptor{

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
		
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		
		String projectName = (String)request.getParameter("projectName");
		param.put("projectName", projectName);
		breadcrumbList.add(new Breadcrumb(getUrl("/project/server", param), "Server", false));
		
		String serverName = (String)request.getParameter("serverName");
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		request.setAttribute("breadcrumbActive", serverName);
	}

}
