package jansible.web.manager;

import jansible.mapper.GlobalRoleMapper;
import jansible.mapper.GlobalTaskMapper;
import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTemplateKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleFile;
import jansible.model.database.DbGlobalRoleTemplate;
import jansible.web.manager.role.GeneralFileForm;
import jansible.web.manager.role.UploadForm;
import jansible.web.manager.top.GlobalRoleForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalRoleService {
	@Autowired
	private GlobalRoleMapper roleMapper;
	@Autowired
	private GlobalTaskMapper taskMapper;
	
	@Autowired
	private ManagerFileService fileService;

	public void registRole(GlobalRoleForm form) {
		DbGlobalRole dbGlobalRole = new DbGlobalRole(form);
		roleMapper.insertRole(dbGlobalRole);
		fileService.outputRoleData(dbGlobalRole);
	}

	public List<DbGlobalRole> getRoleList(){
		return roleMapper.selectRoleList();
	}

	public void deleteRole(GlobalRoleKey key){
		roleMapper.deleteRole(key);
		roleMapper.deleteDbFileByRole(key);
		roleMapper.deleteDbTemplateByRole(key);
		taskMapper.deleteTaskByRole(key);
		taskMapper.deleteTaskDetailByRole(key);
		taskMapper.deleteTaskConditionalByRole(key);
		roleMapper.deleteDbRoleVariableByRole(key);
		fileService.deleteRoleDir(key);
	}

	public void registFile(UploadForm form) {
		DbGlobalRoleFile dbGlobalRoleFile = createDbFile(form);
		roleMapper.insertDbFile(dbGlobalRoleFile);
	}

	public void deleteFile(GeneralFileForm form){
		GlobalRoleFileKey key = new GlobalRoleFileKey(form);
		key.setFileName(form.getFileName());
		roleMapper.deleteDbFile(key);
	}

	public void deleteTemplate(GeneralFileForm form){
		GlobalRoleTemplateKey key = new GlobalRoleTemplateKey(form);
		key.setTemplateName(form.getFileName());
		roleMapper.deleteDbTemplate(key);
	}

	private DbGlobalRoleFile createDbFile(UploadForm form) {
		DbGlobalRoleFile dbGlobalRoleFile = new DbGlobalRoleFile(form);
		dbGlobalRoleFile.setFileName(form.getFileName());
		return dbGlobalRoleFile;
	}

	public void registTemplate(UploadForm form) {
		DbGlobalRoleTemplate dbGlobalRoleTemplate = createDbTemplate(form);
		roleMapper.insertDbTemplate(dbGlobalRoleTemplate);
	}

	private DbGlobalRoleTemplate createDbTemplate(UploadForm form) {
		DbGlobalRoleTemplate dbGlobalRoleTemplate = new DbGlobalRoleTemplate(form);
		dbGlobalRoleTemplate.setTemplateName(form.getFileName());
		return dbGlobalRoleTemplate;
	}

	public List<DbGlobalRoleFile> getDbFileList(GlobalRoleKey key){
		return roleMapper.selectDbFileList(key);
	}
	
	public List<DbGlobalRoleTemplate> getDbTemplateList(GlobalRoleKey key){
		return roleMapper.selectDbTemplateList(key);
	}
}
