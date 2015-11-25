package jansible.model.common;

public class GlobalRoleFileKey extends GlobalRoleKey{
	private String fileName;
	
	public GlobalRoleFileKey(){}

	public GlobalRoleFileKey(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public GlobalRoleFileKey(GlobalRoleKey key, String fileName) {
		this(key);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
