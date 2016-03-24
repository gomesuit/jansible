package jansible.web;

public class Breadcrumb {
	private String url;
	private String name;
	
	public Breadcrumb(String url, String name) {
		this.url = url;
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	public String getName() {
		return name;
	}
}
