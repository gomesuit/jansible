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
		for(SideMenuUrl sideMenuUrl : SideMenuUrl.values()){
			SideMenu sideMenu = createSideMenu(sideMenuUrl, projectName, pageName);
			menuList.add(sideMenu);
		}
		
		request.setAttribute("menuList", menuList);
	}
	
	private SideMenu createSideMenu(SideMenuUrl sideMenuUrl, String projectName, String pageName){
		String url = sideMenuUrl.getUrl() + "?projectName=" + projectName;
		String name = sideMenuUrl.name();
		boolean active = pageName.equals(sideMenuUrl.getTemplatePath());
		
		SideMenu sideMenu = new SideMenu(url, name, active);
		
		return sideMenu;
	}
	
	private enum SideMenuUrl {
		TOP			("/project/view",			"project/project/top"),
		Environment	("/project/environment",	"project/project/environment"),
		Server		("/project/viewServer",		"project/project/server"),
		Group		("/project/viewGroup",		"project/project/group"),
		Role		("/project/viewRole",		"project/project/role"),
		Apply		("/project/viewApply",		"project/project/apply");
		
		private String url;
		private String templatePath;
		
		SideMenuUrl(String url, String templatePath){
			this.url = url;
			this.templatePath = templatePath;
		}

		public String getUrl() {
			return url;
		}

		public String getTemplatePath() {
			return templatePath;
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

}
