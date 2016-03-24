package jansible.web.project.breadcrunb;

import jansible.web.Breadcrumb;
import jansible.web.BreadcrumbInterceptorBase;
import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbEnvironmentInterceptor extends BreadcrumbInterceptorBase{

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject environment = SideMenuUrlProject.Environment;

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(environment.getUrl(), param, environment.name()));
		breadcrumbActiveList.add(requestParam.get("environmentName"));
	}

}
