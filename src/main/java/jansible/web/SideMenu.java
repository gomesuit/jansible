package jansible.web;

public class SideMenu {
	private String url;
	private String name;
	private boolean active;
	
	public SideMenu(String url, String name, boolean active) {
		super();
		this.url = url;
		this.name = name;
		this.active = active;
	}
	
	public String getUrl() {
		return url;
	}
	public String getName() {
		return name;
	}
	public boolean isActive() {
		return active;
	}
}
