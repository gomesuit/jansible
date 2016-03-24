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
		
		registry.addInterceptor(new BreadcrumbProjectInterceptor()).addPathPatterns("/project/view");
		registry.addInterceptor(new BreadcrumbEnvironmentInterceptor()).addPathPatterns("/project/environment/**");
		registry.addInterceptor(new BreadcrumbServerInterceptor()).addPathPatterns("/project/server/**");
		registry.addInterceptor(new BreadcrumbGroupInterceptor()).addPathPatterns("/project/group/**");
		registry.addInterceptor(new BreadcrumbRoleInterceptor()).addPathPatterns("/project/role/**");
		registry.addInterceptor(new BreadcrumbApplyInterceptor()).addPathPatterns("/project/apply/**");
	}
}
