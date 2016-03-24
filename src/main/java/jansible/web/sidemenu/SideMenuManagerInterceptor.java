package jansible.web.sidemenu;

import jansible.web.SideMenuUrlManager;

import java.util.List;
import java.util.Map;

public class SideMenuManagerInterceptor extends SideMenuInterceptorBase {

	private SideMenu createSideMenu(SideMenuUrlManager sideMenuUrl, String pageName){
		String url = sideMenuUrl.getUrl();
		String name = sideMenuUrl.name();
		boolean active = pageName.equals(sideMenuUrl.getTemplatePath());
		
		SideMenu sideMenu = new SideMenu(url, name, active);
		
		return sideMenu;
	}
	
	@Override
	void createSideMenuList(String pageName, List<SideMenu> menuList,
			Map<String, String> requestParam) {
		
		for(SideMenuUrlManager sideMenuUrl : SideMenuUrlManager.values()){
			SideMenu sideMenu = createSideMenu(sideMenuUrl, pageName);
			menuList.add(sideMenu);
		}
	}

}
