package jansible.web.breadcrunb;

import jansible.web.SideMenuUrlProject;
import jansible.web.UrlTemplateMapper;

import java.util.List;
import java.util.Map;

public class BreadcrumbTaskInterceptor extends BreadcrumbInterceptorBase {

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject role = SideMenuUrlProject.Role;
		
		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(role.getUrl(), param, role.name()));
		
		param = createUrlParam(requestParam, "projectName", "roleName");
		breadcrumbList.add(createBreadcrumb(UrlTemplateMapper.ROLE_DETAIL.getUrl(), param, requestParam.get("roleName")));
		
		breadcrumbActiveList.add(requestParam.get("taskId"));
	}

}
