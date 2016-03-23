package jansible.web.project.server.form;

import jansible.model.common.ServerKey;
import jansible.model.common.ServerParameterKey;
import jansible.model.common.ServerVariableKey;

public class ServerParameterForm extends ServerParameterKey{
	private String parameterValue;
	
	public ServerParameterForm(){}

	public ServerParameterForm(ServerKey serverKey){
		super(serverKey);
	}
	
	public ServerParameterForm(ServerVariableKey serverVariableKey){
		super(serverVariableKey, serverVariableKey.getVariableName());
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
