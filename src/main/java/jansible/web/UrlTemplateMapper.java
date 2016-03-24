package jansible.web;

public enum UrlTemplateMapper {
	TOP					("/",							"project/top"),
	PROJECT				("/project/view",				"project/project/top"),
	ENVIRONMENT			("/project/environment",		"project/environment/top"),
	ENVIRONMENT_DETAIL	("/project/environment/detail",	"project/environment/detail"),
	SERVER				("/project/server",				"project/server/top"),
	SERVER_DETAIL		("/project/server/detail",		"project/server/detail"),
	GROUP				("/project/group",				"project/group/top"),
	GROUP_DETAIL		("/project/group/detail",		"project/group/detail"),
	ROLE				("/project/role",				"project/role/top"),
	ROLE_DETAIL			("/project/role/detail",		"project/role/detail"),
	TASK_DETAIL			("/project/task/detail",		"project/role/task/detail"),
	APPLY				("/project/apply",				"project/apply/top"),
	APPLY_GROUP			("/project/apply/group",		"project/apply/group"),
	APPLY_SERVER		("/project/apply/server",		"project/apply/server"),
	APPLY_RESULT		("/project/jenkins/result",		"project/apply/result/detail"),
	
	MANAGER_TOP			("/manager",					"manager/top"),
	MANAGER_ROLE_DETAIL	("/manager/role/view",			"manager/role/top"),
	MANAGER_TASK_DETAIL	("/manager/task/view",			"manager/task/top"),
	MANAGER_MODULE		("/manager/module",				"manager/module/moduleList");

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
