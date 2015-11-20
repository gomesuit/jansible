package jansible.git;

public interface GitOperationInfo {
	public String getUserName();
	public void setUserName(String userName);
	
	public String getPassword();
	public void setPassword(String password);
	
	public String getComment();
	public void setComment(String comment);
}
