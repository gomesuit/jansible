package jansible.web.sidemenu;

import java.util.List;
import java.util.Map;

public class PageNameInterceptor extends SideMenuInterceptorBase {
	
	private enum SideMenuUrl {
		TOP			("/project/view",			"project/project/top"),
		Environment	("/project/environment",	"project/project/environment"),
		Server		("/project/server",			"project/project/server"),
		Group		("/project/group",			"project/project/group"),
		Role		("/project/role",			"project/project/role"),
		Apply		("/project/apply",			"project/project/apply");
		
		private String url;
		private String templatePath;
		
		SideMenuUrl(String url, String templatePath){
			this.url = url;
			this.templatePath = templatePath;
		}

		public String getUrl() {
			return url;
		}

		public String getTemplatePath() {
			return templatePath;
		}
	}

	private SideMenu createSideMenu(SideMenuUrl sideMenuUrl, String projectName, String pageName){
		String url = sideMenuUrl.getUrl() + "?projectName=" + projectName;
		String name = sideMenuUrl.name();
		boolean active = pageName.equals(sideMenuUrl.getTemplatePath());
		
		SideMenu sideMenu = new SideMenu(url, name, active);
		
		return sideMenu;
	}
	
	@Override
	void createSideMenuList(String pageName, List<SideMenu> menuList, Map<String, String> requestParam) {
		String projectName = requestParam.get("projectName");

		for(SideMenuUrl sideMenuUrl : SideMenuUrl.values()){
			SideMenu sideMenu = createSideMenu(sideMenuUrl, projectName, pageName);
			menuList.add(sideMenu);
		}
	}

}
