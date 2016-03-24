package jansible.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class HandlerInterceptorBase implements HandlerInterceptor {
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
			HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
		Map<String, String> requestParam = createRequestParam(request, PARAMETER_NAME_LIST);
		postHandleCore(request, requestParam);
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

	protected abstract void postHandleCore(HttpServletRequest request, Map<String, String> requestParam);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		return true;
	}

}
