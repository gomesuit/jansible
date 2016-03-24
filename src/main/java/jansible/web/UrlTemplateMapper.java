package jansible.web;

public enum UrlTemplateMapper {
	PROJECT				("/project/view",				"project/project/top"),
	ENVIRONMENT			("/project/environment",		"project/project/environment"),
	ENVIRONMENT_DETAIL	("/project/environment/view",	"project/environment/top"),
	SERVER				("/project/server",				"project/project/server"),
	SERVER_DETAIL		("/project/server/view",		"project/server/top"),
	GROUP				("/project/group",				"project/project/group"),
	GROUP_DETAIL		("/project/group/view",			"project/service_group/top"),
	ROLE				("/project/role",				"project/project/role"),
	ROLE_DETAIL			("/project/role/view",			"project/role/top"),
	TASK_DETAIL			("/project/task/view",			"project/task/top"),
	APPLY				("/project/apply",				"project/project/apply"),
	APPLY_GROUP			("/project/apply/view",			"project/apply/group"),
	APPLY_SERVER		("/project/applyServer/view",	"project/apply/server"),
	APPLY_RESULT		("/project/jenkins/result",		"project/result/top");

	private String url;
	private String templatePath;
	
	UrlTemplateMapper(String url, String templatePath){
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
