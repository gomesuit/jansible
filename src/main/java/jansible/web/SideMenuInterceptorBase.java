package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class SideMenuInterceptorBase implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SideMenuInterceptorBase.class);
	
	private static final String[] PARAMETER_NAME_LIST = {
		"projectName",
		"environmentName",
		"serverName",
		"groupName",
		"roleName",
		"taskId",
		"applyHistroyId"
		};

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
		logger.info("RequestURL : " + request.getRequestURI());
		
		String pageName = (String)request.getAttribute("pageName");
		if(pageName == null) return;
		
		Map<String, String> requestParam = createRequestParam(request, PARAMETER_NAME_LIST);
		
		List<SideMenu> menuList = new ArrayList<>();
		createSideMenuList(pageName, menuList, requestParam);
		
		request.setAttribute("menuList", menuList);
	}
	
	private Map<String, String> createRequestParam(HttpServletRequest request, String[] parameterNameList){
		Map<String, String> requestParam = new HashMap<>();
		
		for(String parameterName : parameterNameList){
			String parameterValue = (String)request.getParameter(parameterName);
			if(!StringUtils.isEmpty(parameterValue)){
				requestParam.put(parameterName, parameterValue);
			}
		}
		
		return requestParam;
	}
	
	abstract void createSideMenuList(String pageName, List<SideMenu> menuList, Map<String, String> requestParam);

	@Override
	public boolean preHandle(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

}
