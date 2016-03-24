package jansible.web.manager.breadcrunb;

import jansible.web.Breadcrumb;
import jansible.web.BreadcrumbInterceptorBase;
import jansible.web.UrlTemplateMapper;

import java.util.List;
import java.util.Map;

public class BreadcrumbGlobalRoleTaskInterceptor extends BreadcrumbInterceptorBase{

	@Override
	protected void createBreadCrumb(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {

		UrlTemplateMapper mapper = UrlTemplateMapper.MANAGER_TOP;
		
		breadcrumbList.add(createBreadcrumb(mapper.getUrl(), null, "Role"));
		Map<String, String> param = createUrlParam(requestParam, "roleName");
		breadcrumbList.add(createBreadcrumb(UrlTemplateMapper.MANAGER_ROLE_DETAIL.getUrl(), param, requestParam.get("roleName")));
		
		breadcrumbActiveList.add(requestParam.get("taskId"));
	}

}
