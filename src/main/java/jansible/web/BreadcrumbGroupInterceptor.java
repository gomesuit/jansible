package jansible.web;

import java.util.List;
import java.util.Map;

public class BreadcrumbGroupInterceptor extends BreadcrumbBaseInterceptor{

	@Override
	protected void postHandleCore(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb("/project/group", param, "Group"));
		breadcrumbActiveList.add(requestParam.get("environmentName"));
		breadcrumbActiveList.add(requestParam.get("groupName"));
		
	}

}
