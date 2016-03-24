package jansible.web.project.breadcrunb;

import jansible.web.Breadcrumb;
import jansible.web.BreadcrumbInterceptorBase;
import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbServerInterceptor extends BreadcrumbInterceptorBase{

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject server = SideMenuUrlProject.Server;
		
		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(server.getUrl(), param, server.name()));
		breadcrumbActiveList.add(requestParam.get("serverName"));
	}

}
