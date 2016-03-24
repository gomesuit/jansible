package jansible.web.breadcrunb;

import java.util.List;
import java.util.Map;

public class BreadcrumbRoleInterceptor extends BreadcrumbInterceptorBase {

	@Override
	protected void postHandleCore(Map<String, String> requestParam,
			List<Breadcrumb> breadcrumbList, List<String> breadcrumbActiveList) {
		
		String taskId = requestParam.get("taskId");
		if(taskId != null){
			Map<String, String> param = createUrlParam(requestParam, "projectName");
			breadcrumbList.add(createBreadcrumb("/project/role", param, "Role"));
			param = createUrlParam(requestParam, "projectName", "roleName");
			breadcrumbList.add(createBreadcrumb("/project/role/view", param, requestParam.get("roleName")));
			breadcrumbActiveList.add(requestParam.get("taskId"));
		}else{
			Map<String, String> param = createUrlParam(requestParam, "projectName");
			breadcrumbList.add(createBreadcrumb("/project/role", param, "Role"));
			breadcrumbActiveList.add(requestParam.get("roleName"));
		}
		
	}

}
