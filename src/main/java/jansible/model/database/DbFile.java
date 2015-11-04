package jansible.model.database;

import jansible.model.common.FileKey;

public class DbFile extends FileKey{
	
	public DbFile(){}

	public DbFile(String projectName, String roleName, String fileName) {
		super(projectName, roleName, fileName);
	}
}
