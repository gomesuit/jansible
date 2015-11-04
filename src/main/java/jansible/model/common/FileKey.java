package jansible.model.common;

public class FileKey extends RoleKey{
	private String fileName;
	
	public FileKey(){}

	public FileKey(String projectName, String roleName, String fileName) {
		super(projectName, roleName);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
