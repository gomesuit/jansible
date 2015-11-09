package jansible.web.project.form;

import jansible.model.common.ServerKey;
import jansible.model.common.ServerVariableKey;

public class ServerVariableForm extends ServerVariableKey implements InterfaceVariable{
	private String value;
	
	public ServerVariableForm(){}

	public ServerVariableForm(ServerKey serverKey){
		super(serverKey);
	}
	
	public ServerVariableForm(ServerVariableKey serverVariableKey){
		super(serverVariableKey, serverVariableKey.getVariableName());
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
