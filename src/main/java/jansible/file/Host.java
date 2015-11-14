package jansible.file;

import java.util.ArrayList;
import java.util.List;

public class Host {
	private String serverName;
	private List<Parameter> parameterList = new ArrayList<>();
	
	public void addParameter(String parameterName, String parameterValue){
		parameterList.add(new Parameter(parameterName, parameterValue));
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public List<Parameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
}
