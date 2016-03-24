package jansible.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public abstract class BreadcrumbInterceptorBase extends HandlerInterceptorBase {
	
	protected void postHandleCore(HttpServletRequest request, Map<String, String> requestParam){
		List<Breadcrumb> breadcrumbList = new ArrayList<>();
		List<String> breadcrumbActiveList = new ArrayList<>();
		
		createBreadCrumb(requestParam, breadcrumbList, breadcrumbActiveList);
		
		request.setAttribute("breadcrumbList", breadcrumbList);
		request.setAttribute("breadcrumbActiveList", breadcrumbActiveList);
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

	abstract protected void createBreadCrumb(Map<String, String> requestParam, List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList);

}
