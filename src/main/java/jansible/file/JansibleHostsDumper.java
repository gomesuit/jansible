package jansible.file;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class JansibleHostsDumper {
	
	public String getString(List<HostGroup> hostGroupList){
		StringBuffer stringBuffer = new StringBuffer();
		for(HostGroup hostGroup : hostGroupList){
			stringBuffer.append(getString(hostGroup));
			stringBuffer.append(System.lineSeparator());
		}
		return stringBuffer.toString();
	}
	
	private String getString(HostGroup hostGroup){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getGroupString(hostGroup.getGroupName()));
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(getHostListString(hostGroup.getHostList()));
		return stringBuffer.toString();
	}
	
	private String getGroupString(String groupName){
		return "[" + groupName + "]";
	}
	
	private String getHostListString(List<String> hostList){
		StringBuffer stringBuffer = new StringBuffer();
		for(String host : hostList){
			stringBuffer.append(host);
			stringBuffer.append(System.lineSeparator());
		}
		
		return stringBuffer.toString();
	}
}
