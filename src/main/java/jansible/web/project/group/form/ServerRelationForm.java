package jansible.web.project.group.form;

import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;

public class ServerRelationForm extends ServerRelationKey{
	public ServerRelationForm(){}

	public ServerRelationForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey);
	}

	public ServerRelationForm(ServerRelationKey serverRelationKey){
		super(serverRelationKey, serverRelationKey.getServerName());
	}
}
