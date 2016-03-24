package jansible.web.breadcrunb;

import java.util.List;
import java.util.Map;

public class BreadcrumbProjectInterceptor extends BreadcrumbInterceptorBase{

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {
		
		Map<String, String> param = createUrlParam(requestParam, "projectName");
		
		breadcrumbList.add(createBreadcrumb("/project/view", param, "Project"));
		breadcrumbActiveList.add(requestParam.get("projectName"));
	}

}
