package jansible.model.database;

import jansible.model.common.FileKey;
import jansible.model.common.RoleKey;

public class DbFile extends FileKey{
	
	public DbFile(){}

	public DbFile(RoleKey roleKey){
		super(roleKey);
	}

	public DbFile(FileKey fileKey) {
		super(fileKey, fileKey.getFileName());
	}
}
