package jansible.web;

import jansible.web.breadcrunb.BreadcrumbApplyInterceptor;
import jansible.web.breadcrunb.BreadcrumbEnvironmentInterceptor;
import jansible.web.breadcrunb.BreadcrumbGroupInterceptor;
import jansible.web.breadcrunb.BreadcrumbProjectInterceptor;
import jansible.web.breadcrunb.BreadcrumbResultInterceptor;
import jansible.web.breadcrunb.BreadcrumbRoleInterceptor;
import jansible.web.breadcrunb.BreadcrumbServerInterceptor;
import jansible.web.breadcrunb.BreadcrumbTaskInterceptor;
import jansible.web.sidemenu.SideMenuManagerInterceptor;
import jansible.web.sidemenu.SideMenuProjectInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SideMenuProjectInterceptor()).addPathPatterns("/project/**");
		registry.addInterceptor(new SideMenuManagerInterceptor()).addPathPatterns("/manager/**");
		
		registry.addInterceptor(new BreadcrumbProjectInterceptor()).addPathPatterns("/project/view");
		registry.addInterceptor(new BreadcrumbEnvironmentInterceptor()).addPathPatterns("/project/environment/**");
		registry.addInterceptor(new BreadcrumbServerInterceptor()).addPathPatterns("/project/server/**");
		registry.addInterceptor(new BreadcrumbGroupInterceptor()).addPathPatterns("/project/group/**");
		registry.addInterceptor(new BreadcrumbRoleInterceptor()).addPathPatterns("/project/role/**");
		registry.addInterceptor(new BreadcrumbTaskInterceptor()).addPathPatterns("/project/task/**");
		registry.addInterceptor(new BreadcrumbApplyInterceptor()).addPathPatterns("/project/apply/**", "/project/applyServer/**");
		registry.addInterceptor(new BreadcrumbResultInterceptor()).addPathPatterns("/project/jenkins/**");
	}
}
