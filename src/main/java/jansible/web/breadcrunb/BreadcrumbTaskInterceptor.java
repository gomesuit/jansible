package jansible.web.breadcrunb;

import java.util.List;
import java.util.Map;

public class BreadcrumbTaskInterceptor extends BreadcrumbInterceptorBase {

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {
		
		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb("/project/role", param, "Role"));
		
		param = createUrlParam(requestParam, "projectName", "roleName");
		breadcrumbList.add(createBreadcrumb("/project/role/view", param, requestParam.get("roleName")));
		
		breadcrumbActiveList.add(requestParam.get("taskId"));
	}

}
