package jansible.git;

public interface GitOperationInfo extends GitCredentialInfo{
	public String getComment();
	public void setComment(String comment);
}
