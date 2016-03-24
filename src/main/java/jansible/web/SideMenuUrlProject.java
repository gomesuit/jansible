package jansible.web;

public enum SideMenuUrlProject {
	TOP			(UrlTemplateMapper.PROJECT),
	Environment	(UrlTemplateMapper.ENVIRONMENT),
	Server		(UrlTemplateMapper.SERVER),
	Group		(UrlTemplateMapper.GROUP),
	Role		(UrlTemplateMapper.ROLE),
	Apply		(UrlTemplateMapper.APPLY);
	
	private UrlTemplateMapper urlTemplateMapper;
	
	SideMenuUrlProject(UrlTemplateMapper urlTemplateMapper){
		this.urlTemplateMapper = urlTemplateMapper;
	}

	public String getUrl() {
		return urlTemplateMapper.getUrl();
	}

	public String getTemplatePath() {
		return urlTemplateMapper.getTemplatePath();
	}
}
