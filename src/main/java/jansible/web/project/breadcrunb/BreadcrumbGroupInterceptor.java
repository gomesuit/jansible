package jansible.web.project.breadcrunb;

import jansible.web.Breadcrumb;
import jansible.web.BreadcrumbInterceptorBase;
import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbGroupInterceptor extends BreadcrumbInterceptorBase{

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject group = SideMenuUrlProject.Group;

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(group.getUrl(), param, group.name()));
		breadcrumbActiveList.add(requestParam.get("environmentName"));
		breadcrumbActiveList.add(requestParam.get("groupName"));
		
	}

}
