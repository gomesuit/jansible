package jansible.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ManagerPageNameInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {

		if(request.getMethod().equals("POST")){
			return;
		}
		
		String pageName = (String)request.getAttribute("pageName");
		
		List<SideMenu> menuList = new ArrayList<>();
		
		menuList.add(new SideMenu("/manager", "TOP", pageName.equals("manager/top")));
		menuList.add(new SideMenu("/manager/module", "MODULE", pageName.equals("manager/module/moduleList")));
		
		request.setAttribute("menuList", menuList);
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

}
