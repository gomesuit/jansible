package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class BreadcrumbEnvironmentInterceptor extends BreadcrumbBaseInterceptor{

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		
		String projectName = (String)request.getParameter("projectName");
		
		param.put("projectName", projectName);
		breadcrumbList.add(new Breadcrumb(getUrl("/project/environment", param), "Environment", false));
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		
		String environmentName = (String)request.getParameter("environmentName");
		request.setAttribute("breadcrumbActive", environmentName);
	}

}
