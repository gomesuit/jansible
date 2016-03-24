package jansible.web.breadcrunb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class BreadcrumbInterceptorBase implements HandlerInterceptor {
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
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	
	protected Breadcrumb createBreadcrumb(String baseUrl, Map<String, String> param, String displayName){
		String url = getUrl(baseUrl, param);
		return new Breadcrumb(url, displayName);
	}
	
	protected Map<String, String> createUrlParam(Map<String, String> requestParam, String... paramNameList){
		Map<String, String> param = new HashMap<>();
		
		for(String paramName : paramNameList){
			param.put(paramName, requestParam.get(paramName));
		}
		
		return param;
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

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
		
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		List<String> breadcrumbActiveList = new ArrayList<>();
		
		Map<String, String> requestParam = createRequestParam(request, PARAMETER_NAME_LIST);
		
		postHandleCore(requestParam, breadcrumbList, breadcrumbActiveList);
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		request.setAttribute("breadcrumbActiveList", breadcrumbActiveList);
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
	
	abstract protected void postHandleCore(Map<String, String> requestParam, List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList);

}
