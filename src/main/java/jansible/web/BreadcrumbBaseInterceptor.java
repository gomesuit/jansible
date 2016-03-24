package jansible.web;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public abstract class BreadcrumbBaseInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	
	protected String getUrl(String baseUrl, Map<String, String> param){
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

}
