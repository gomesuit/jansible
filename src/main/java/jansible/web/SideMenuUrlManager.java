package jansible.web;

public enum SideMenuUrlManager {
	TOP				(UrlTemplateMapper.MANAGER_TOP),
	GLOBAL_ROLE		(UrlTemplateMapper.MANAGER_ROLE),
	MODULE			(UrlTemplateMapper.MANAGER_MODULE);

	private UrlTemplateMapper urlTemplateMapper;
	
	SideMenuUrlManager(UrlTemplateMapper urlTemplateMapper){
		this.urlTemplateMapper = urlTemplateMapper;
	}

	public String getUrl() {
		return urlTemplateMapper.getUrl();
	}

	public String getTemplatePath() {
		return urlTemplateMapper.getTemplatePath();
	}
}
