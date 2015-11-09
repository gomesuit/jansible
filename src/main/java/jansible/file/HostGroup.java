package jansible.file;

import java.util.ArrayList;
import java.util.List;

public class HostGroup {
	private String groupName;
	private List<String> hostList = new ArrayList<>();
	
	public void addHost(String host){
		hostList.add(host);
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getHostList() {
		return hostList;
	}
	public void setHostList(List<String> hostList) {
		this.hostList = hostList;
	}
}
