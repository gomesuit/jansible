package jansible.web.manager.role;

import jansible.git.GitOperationInfo;
import jansible.model.common.GlobalRoleKey;

public class GitForm extends GlobalRoleKey implements GitOperationInfo{
	private String userName;
	private String password;
	private String comment;
	
	public GitForm(){}

	public GitForm(GlobalRoleKey key){
		super(key.getRoleName());
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
