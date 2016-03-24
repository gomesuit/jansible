package jansible.web;

public enum SideMenuUrlProject {
	TOP			("/project/view",			"project/project/top"),
	Environment	("/project/environment",	"project/project/environment"),
	Server		("/project/server",			"project/project/server"),
	Group		("/project/group",			"project/project/group"),
	Role		("/project/role",			"project/project/role"),
	Apply		("/project/apply",			"project/project/apply");
	
	private String url;
	private String templatePath;
	
	SideMenuUrlProject(String url, String templatePath){
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
