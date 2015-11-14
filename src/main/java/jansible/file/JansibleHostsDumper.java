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
	
	private String getHostListString(List<Host> hostList){
		StringBuffer stringBuffer = new StringBuffer();
		for(Host host : hostList){
			stringBuffer.append(host.getServerName());
			stringBuffer.append(" ");
			stringBuffer.append(getHostString(host));
			stringBuffer.append(System.lineSeparator());
		}
		
		return stringBuffer.toString();
	}
	
	private String getHostString(Host host){
		StringBuffer stringBuffer = new StringBuffer();
		for(Parameter parameter : host.getParameterList()){
			stringBuffer.append(getParameterString(parameter));
		}
		
		return stringBuffer.toString();
	}
	
	private String getParameterString(Parameter parameter){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(parameter.getParameterName());
		stringBuffer.append("=");
		stringBuffer.append(parameter.getParameterValue());
		
		return stringBuffer.toString();
	}
}
