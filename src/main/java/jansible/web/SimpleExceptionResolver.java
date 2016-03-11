package jansible.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class SimpleExceptionResolver implements HandlerExceptionResolver {
	 private Logger log = LoggerFactory.getLogger(SimpleExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		log.error("occur Exception !!", ex);
		
		FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);

		List<String> alertList = new ArrayList<>();
		alertList.add(ex.getClass().getName() + " : " + ex.getMessage());
		
		outputFlashMap.put("alertList", alertList);

		ModelAndView model = new ModelAndView();
		String referer = request.getHeader("Referer");
		model.setViewName("redirect:" + referer);
		
		return model;
	}

}
