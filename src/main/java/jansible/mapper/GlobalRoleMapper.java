package jansible.mapper;

import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTemplateKey;
import jansible.model.common.GlobalRoleVariableKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleFile;
import jansible.model.database.DbGlobalRoleTemplate;
import jansible.model.database.DbGlobalRoleVariable;

import java.util.List;

public interface GlobalRoleMapper {
	void insertRole(DbGlobalRole dbGlobalRole);
	List<DbGlobalRole> selectRoleList();
	DbGlobalRole selectRole(GlobalRoleKey globalRoleKey);
	void deleteRole(GlobalRoleKey globalRoleKey);
	
	void insertDbFile(DbGlobalRoleFile dbGlobalRoleFile);
	List<DbGlobalRoleFile> selectDbFileList(GlobalRoleKey globalRoleKey);
	void deleteDbFile(GlobalRoleFileKey globalRoleFileKey);
	void deleteDbFileByRole(GlobalRoleKey globalRoleKey);
	
	void insertDbTemplate(DbGlobalRoleTemplate dbGlobalRoleTemplate);
	List<DbGlobalRoleTemplate> selectDbTemplateList(GlobalRoleKey globalRoleKey);
	void deleteDbTemplate(GlobalRoleTemplateKey globalRoleTemplateKey);
	void deleteDbTemplateByRole(GlobalRoleKey globalRoleKey);

	void insertDbRoleVariable(DbGlobalRoleVariable dbGlobalRoleVariable);
	List<DbGlobalRoleVariable> selectDbRoleVariableList(GlobalRoleKey globalRoleKey);
	void deleteDbRoleVariable(GlobalRoleVariableKey globalRoleVariableKey);
	void deleteDbRoleVariableByRole(GlobalRoleKey globalRoleKey);
}
