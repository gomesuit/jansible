package jansible.web.sidemenu;

import jansible.web.HandlerInterceptorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SideMenuInterceptorBase extends HandlerInterceptorBase {
	private static final Logger logger = LoggerFactory.getLogger(SideMenuInterceptorBase.class);

	protected void postHandleCore(HttpServletRequest request, Map<String, String> requestParam){
		logger.info("RequestURL : " + request.getRequestURI());
		
		String pageName = (String)request.getAttribute("pageName");
		if(pageName == null) return;
		
		List<SideMenu> menuList = new ArrayList<>();
		createSideMenuList(pageName, menuList, requestParam);
		
		request.setAttribute("menuList", menuList);
	}
	
	abstract void createSideMenuList(String pageName, List<SideMenu> menuList, Map<String, String> requestParam);

}
