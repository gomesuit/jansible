package jansible.web;

public enum SideMenuUrlManager {
	TOP		(UrlTemplateMapper.MANAGER_TOP),
	MODULE	(UrlTemplateMapper.MANAGER_MODULE);

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
