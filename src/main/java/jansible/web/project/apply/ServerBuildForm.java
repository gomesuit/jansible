package jansible.web.project.apply;

import jansible.git.GitOperationInfo;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;

public class ServerBuildForm extends ServerRelationKey implements GitOperationInfo{
	private String userName;
	private String password;
	private String comment;
	
	public ServerBuildForm(){}
	
	public ServerBuildForm(ServiceGroupKey serviceGroupKey){
		super(serviceGroupKey, serviceGroupKey.getGroupName());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
