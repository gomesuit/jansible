package jansible.web.manager;

import jansible.mapper.GlobalRoleMapper;
import jansible.mapper.GlobalTaskMapper;
import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTemplateKey;
import jansible.model.common.GlobalRoleVariableKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleFile;
import jansible.model.database.DbGlobalRoleTag;
import jansible.model.database.DbGlobalRoleTagVariable;
import jansible.model.database.DbGlobalRoleTemplate;
import jansible.model.database.DbGlobalRoleVariable;
import jansible.web.manager.role.GeneralFileForm;
import jansible.web.manager.role.GitForm;
import jansible.web.manager.role.RoleVariableForm;
import jansible.web.manager.role.UploadForm;
import jansible.web.manager.top.GlobalRoleForm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class GlobalRoleService {
	@Autowired
	private GlobalRoleMapper roleMapper;
	@Autowired
	private GlobalTaskMapper taskMapper;
	
	@Autowired
	private ManagerFileService fileService;
	@Autowired
	private ManagerGitService gitService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public void registRole(GlobalRoleForm form) throws Exception {
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {	    	
			DbGlobalRole dbGlobalRole = new DbGlobalRole(form);
			dbGlobalRole.setRepositoryUrl(form.getRepositoryUrl());
			roleMapper.insertRole(dbGlobalRole);
			gitService.cloneRepository(form);
			fileService.outputRoleData(dbGlobalRole);
		} catch (Exception e) {
			transactionManager.rollback(status);
			fileService.deleteRoleDir(form);
			throw e;
		}
		transactionManager.commit(status);
	}
	
	public void registRoleTag(GitForm form, String tagName){
		DbGlobalRoleTag dbGlobalRoleTag = new DbGlobalRoleTag(form);
		dbGlobalRoleTag.setTagName(tagName);
		dbGlobalRoleTag.setTagComment(form.getComment());
		roleMapper.insertRoleTag(dbGlobalRoleTag);
		
		List<DbGlobalRoleTagVariable> dbGlobalRoleTagVariableList = createDbGlobalRoleTagVariableList(form, tagName);
		registDbGlobalRoleTagVariableList(dbGlobalRoleTagVariableList);
	}
	
	private void registDbGlobalRoleTagVariableList(List<DbGlobalRoleTagVariable> dbGlobalRoleTagVariableList){
		for(DbGlobalRoleTagVariable dbGlobalRoleTagVariable : dbGlobalRoleTagVariableList){
			roleMapper.insertRoleTagVariable(dbGlobalRoleTagVariable);
		}
	}
	
	private List<DbGlobalRoleTagVariable> createDbGlobalRoleTagVariableList(GlobalRoleKey key, String tagName){
		List<DbGlobalRoleTagVariable> dbGlobalRoleTagVariableList = new ArrayList<>();
		List<DbGlobalRoleVariable> dbGlobalRoleVariableList = roleMapper.selectDbRoleVariableList(key);
		for(DbGlobalRoleVariable dbGlobalRoleVariable : dbGlobalRoleVariableList){
			DbGlobalRoleTagVariable dbGlobalRoleTagVariable = new DbGlobalRoleTagVariable(dbGlobalRoleVariable, tagName);
			dbGlobalRoleTagVariableList.add(dbGlobalRoleTagVariable);
		}
		return dbGlobalRoleTagVariableList;
	}

	public List<DbGlobalRoleTag> getRoleTagList(GlobalRoleKey key){
		return roleMapper.selectRoleTagList(key);
	}

	public List<DbGlobalRole> getRoleList(){
		return roleMapper.selectRoleList();
	}

	public DbGlobalRole getRole(GlobalRoleKey key){
		return roleMapper.selectRole(key);
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
	
	public void registRoleVariable(RoleVariableForm form) {
		DbGlobalRoleVariable dbRoleVariable = createDbRoleVariable(form);
		roleMapper.insertDbRoleVariable(dbRoleVariable);
		
		fileService.outputRoleVariableData(form);
	}
	
	private DbGlobalRoleVariable createDbRoleVariable(RoleVariableForm form) {
		DbGlobalRoleVariable dbRoleVariable = new DbGlobalRoleVariable(form);
		dbRoleVariable.setValue(form.getValue());
		return dbRoleVariable;
	}

	public List<DbGlobalRoleVariable> getDbRoleVariableList(GlobalRoleKey roleKey){
		return roleMapper.selectDbRoleVariableList(roleKey);
	}

	public void deleteRoleVariable(GlobalRoleVariableKey roleVariableKey){
		roleMapper.deleteDbRoleVariable(roleVariableKey);
	}
}
