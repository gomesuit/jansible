package jansible.web;

public enum SideMenuUrlManager {
	TOP		("/manager",		"manager/top"),
	MODULE	("/manager/module",	"manager/module/moduleList");
	
	private String url;
	private String templatePath;
	
	SideMenuUrlManager(String url, String templatePath){
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
