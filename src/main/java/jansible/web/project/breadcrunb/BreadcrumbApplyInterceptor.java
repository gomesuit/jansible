package jansible.web.project.breadcrunb;

import jansible.web.Breadcrumb;
import jansible.web.BreadcrumbInterceptorBase;
import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbApplyInterceptor extends BreadcrumbInterceptorBase{
	
	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {
		
		SideMenuUrlProject apply = SideMenuUrlProject.Apply;
		
		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(apply.getUrl(), param, apply.name()));
		
		breadcrumbActiveList.add(requestParam.get("environmentName"));
		breadcrumbActiveList.add(requestParam.get("groupName"));
		breadcrumbActiveList.add(requestParam.get("serverName"));
	}

}
