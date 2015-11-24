package jansible.mapper;

import jansible.model.common.FileKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.TemplateKey;
import jansible.model.database.DbFile;
import jansible.model.database.DbRole;
import jansible.model.database.DbTemplate;

import java.util.List;

public interface RoleMapper {
	void insertRole(DbRole dbRole);
	List<DbRole> selectRoleList(ProjectKey projectKey);
	void deleteRole(RoleKey roleKey);
	void deleteRoleByProject(ProjectKey projectKey);
	
	void insertDbFile(DbFile dbFile);
	List<DbFile> selectDbFileList(RoleKey roleKey);
	void deleteDbFile(FileKey fileKey);
	void deleteDbFileByRole(RoleKey roleKey);
	void deleteDbFileByProject(ProjectKey projectKey);
	
	void insertDbTemplate(DbTemplate dbTemplate);
	List<DbTemplate> selectDbTemplateList(RoleKey roleKey);
	void deleteDbTemplate(TemplateKey templateKey);
	void deleteDbTemplateByRole(RoleKey roleKey);
	void deleteDbTemplateByProject(ProjectKey projectKey);
}
