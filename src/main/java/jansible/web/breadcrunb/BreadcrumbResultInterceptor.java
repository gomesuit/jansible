package jansible.web.breadcrunb;

import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class BreadcrumbResultInterceptor extends BreadcrumbInterceptorBase{
	
	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		SideMenuUrlProject apply = SideMenuUrlProject.Apply;

		Map<String, String> param = createUrlParam(requestParam, "projectName");
		breadcrumbList.add(createBreadcrumb(apply.getUrl(), param, apply.name()));

		breadcrumbActiveList.add("Result");
		breadcrumbActiveList.add(requestParam.get("applyHistroyId"));
	}

}
