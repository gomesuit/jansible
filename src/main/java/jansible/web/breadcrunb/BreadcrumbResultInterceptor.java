package jansible.web.breadcrunb;

import java.util.List;
import java.util.Map;

public class BreadcrumbResultInterceptor extends BreadcrumbInterceptorBase{
	
	@Override
	protected void postHandleCore(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb("/project/apply", param, "Apply"));

		breadcrumbActiveList.add("Result");
		breadcrumbActiveList.add(requestParam.get("applyHistroyId"));
	}

}
