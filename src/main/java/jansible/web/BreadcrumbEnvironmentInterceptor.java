package jansible.web;

import java.util.List;
import java.util.Map;

public class BreadcrumbEnvironmentInterceptor extends BreadcrumbBaseInterceptor{

	@Override
	protected void postHandleCore(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb("/project/environment", param, "Environment"));
		breadcrumbActiveList.add(requestParam.get("environmentName"));
	}

}
