package jansible.web.breadcrunb;

import java.util.List;
import java.util.Map;

public class BreadcrumbApplyInterceptor extends BreadcrumbInterceptorBase{
	
	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb("/project/apply", param, "Apply"));
		breadcrumbActiveList.add(requestParam.get("environmentName"));
		breadcrumbActiveList.add(requestParam.get("groupName"));
		breadcrumbActiveList.add(requestParam.get("serverName"));
	}

}
