package jansible.file;

import java.util.ArrayList;
import java.util.List;

public class HostGroup {
	private String groupName;
	private List<Host> hostList = new ArrayList<>();
	
	public void addHost(Host host){
		hostList.add(host);
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Host> getHostList() {
		return hostList;
	}
	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}
}
