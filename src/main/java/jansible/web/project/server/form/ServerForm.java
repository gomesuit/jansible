package jansible.web.project.server.form;

import jansible.model.common.ProjectKey;
import jansible.model.common.ServerKey;

public class ServerForm extends ServerKey{
	private String environmentName;
	
	public ServerForm(){}
	
	public ServerForm(ProjectKey projectKey) {
		super(projectKey);
	}
	
	public ServerForm(ServerKey serverKey) {
		super(serverKey, serverKey.getServerName());
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
}
