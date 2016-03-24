package jansible.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PageNameInterceptor()).addPathPatterns("/project/**");
		registry.addInterceptor(new ManagerPageNameInterceptor()).addPathPatterns("/manager/**");
		registry.addInterceptor(new BreadcrumbInterceptor()).addPathPatterns("/project/**");
	}
}
