package jansible.model.yamldump;

import java.util.ArrayList;
import java.util.List;

public class StartYaml {
	private String hosts;
	private List<String> roles = new ArrayList<>();
	
	public void addRole(String role){
		roles.add(role);
	}
	
	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
