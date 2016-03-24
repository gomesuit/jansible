package jansible.web.breadcrunb;

import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbRoleInterceptor extends BreadcrumbInterceptorBase {

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject role = SideMenuUrlProject.Role;

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(role.getUrl(), param, role.name()));
		breadcrumbActiveList.add(requestParam.get("roleName"));
		
	}

}
