package jansible.web.sidemenu;

import jansible.web.SideMenuUrlProject;

import java.util.List;
import java.util.Map;

public class SideMenuProjectInterceptor extends SideMenuInterceptorBase {

	private SideMenu createSideMenu(SideMenuUrlProject sideMenuUrl, String projectName, String pageName){
		String url = sideMenuUrl.getUrl() + "?projectName=" + projectName;
		String name = sideMenuUrl.name();
		boolean active = pageName.equals(sideMenuUrl.getTemplatePath());
		
		SideMenu sideMenu = new SideMenu(url, name, active);
		
		return sideMenu;
	}
	
	@Override
	void createSideMenuList(String pageName, List<SideMenu> menuList, Map<String, String> requestParam) {
		String projectName = requestParam.get("projectName");

		for(SideMenuUrlProject sideMenuUrl : SideMenuUrlProject.values()){
			SideMenu sideMenu = createSideMenu(sideMenuUrl, projectName, pageName);
			menuList.add(sideMenu);
		}
	}

}
