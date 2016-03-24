package jansible.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PageNameInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(PageNameInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
		logger.info("RequestURL : " + request.getRequestURI());
		
		String pageName = (String)request.getAttribute("pageName");
		if(pageName == null) return;
		
		
		String projectName = (String)request.getParameter("projectName");
		
		List<SideMenu> menuList = new ArrayList<>();
		menuList.add(new SideMenu("/project/view?projectName=" + projectName, "TOP", pageName.equals("project/project/top")));
		menuList.add(new SideMenu("/project/viewEnvironment?projectName=" + projectName, "Environment", pageName.equals("project/project/environment")));
		menuList.add(new SideMenu("/project/viewServer?projectName=" + projectName, "Server", pageName.equals("project/project/server")));
		menuList.add(new SideMenu("/project/viewGroup?projectName=" + projectName, "Group", pageName.equals("project/project/group")));
		menuList.add(new SideMenu("/project/viewRole?projectName=" + projectName, "Role", pageName.equals("project/project/role")));
		menuList.add(new SideMenu("/project/viewApply?projectName=" + projectName, "Apply", pageName.equals("project/project/apply")));
		
		request.setAttribute("menuList", menuList);
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

}
