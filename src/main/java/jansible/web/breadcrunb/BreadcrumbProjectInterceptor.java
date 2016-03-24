package jansible.web.breadcrunb;

import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbProjectInterceptor extends BreadcrumbInterceptorBase{

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject top = SideMenuUrlProject.TOP;
		
		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(top.getUrl(), param, top.name()));
		breadcrumbActiveList.add(requestParam.get("projectName"));
	}

}
