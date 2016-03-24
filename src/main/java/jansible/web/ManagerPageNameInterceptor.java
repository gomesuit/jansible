package jansible.web;

import java.util.List;
import java.util.Map;

public class ManagerPageNameInterceptor extends SideMenuInterceptorBase {
	
	private enum SideMenuUrl {
		TOP		("/manager",		"manager/top"),
		MODULE	("/manager/module",	"manager/module/moduleList");
		
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

	private SideMenu createSideMenu(SideMenuUrl sideMenuUrl, String pageName){
		String url = sideMenuUrl.getUrl();
		String name = sideMenuUrl.name();
		boolean active = pageName.equals(sideMenuUrl.getTemplatePath());
		
		SideMenu sideMenu = new SideMenu(url, name, active);
		
		return sideMenu;
	}
	
	@Override
	void createSideMenuList(String pageName, List<SideMenu> menuList,
			Map<String, String> requestParam) {
		
		for(SideMenuUrl sideMenuUrl : SideMenuUrl.values()){
			SideMenu sideMenu = createSideMenu(sideMenuUrl, pageName);
			menuList.add(sideMenu);
		}
	}

}
