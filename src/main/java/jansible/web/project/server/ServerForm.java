package jansible.web.project.server;

import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;

public class ServerForm extends ServerKey{
	public ServerForm(){}
	
	public ServerForm(ServiceGroupKey serviceGroupKey) {
		super(serviceGroupKey);
	}
	
	public ServerForm(ServerKey serverKey) {
		super(serverKey, serverKey.getServerName());
	}
}
