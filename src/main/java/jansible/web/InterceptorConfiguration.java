package jansible.web;

import jansible.web.manager.breadcrunb.BreadcrumbGlobalRoleInterceptor;
import jansible.web.manager.breadcrunb.BreadcrumbGlobalRoleTaskInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbApplyInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbEnvironmentInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbGroupInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbProjectInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbResultInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbRoleInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbServerInterceptor;
import jansible.web.project.breadcrunb.BreadcrumbTaskInterceptor;
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
		registry.addInterceptor(new BreadcrumbApplyInterceptor()).addPathPatterns("/project/apply/**");
		registry.addInterceptor(new BreadcrumbResultInterceptor()).addPathPatterns("/project/jenkins/**");

		registry.addInterceptor(new BreadcrumbGlobalRoleInterceptor()).addPathPatterns("/manager/role/**");
		registry.addInterceptor(new BreadcrumbGlobalRoleTaskInterceptor()).addPathPatterns("/manager/task/**");
	}
}
