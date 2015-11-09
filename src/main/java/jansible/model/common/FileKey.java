package jansible.model.common;

public class FileKey extends RoleKey{
	private String fileName;
	
	public FileKey(){}

	public FileKey(RoleKey roleKey){
		super(roleKey, roleKey.getRoleName());
	}

	public FileKey(RoleKey roleKey, String fileName) {
		this(roleKey);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
