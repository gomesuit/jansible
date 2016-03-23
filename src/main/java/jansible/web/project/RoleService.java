package jansible.web.project;

import jansible.mapper.RoleMapper;
import jansible.mapper.TaskMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.FileKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.TemplateKey;
import jansible.model.database.DbFile;
import jansible.model.database.DbRole;
import jansible.model.database.DbTemplate;
import jansible.web.project.role.form.GeneralFileForm;
import jansible.web.project.role.form.RoleForm;
import jansible.web.project.role.form.UploadForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private FileService fileService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private VariableMapper variableMapper;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public void registRole(RoleForm form) {
		DbRole dbRole = new DbRole(form);
		roleMapper.insertRole(dbRole);
		fileService.outputRoleData(dbRole);
	}

	public List<DbRole> getRoleList(ProjectKey projectKey){
		return roleMapper.selectRoleList(projectKey);
	}

	public void deleteRole(RoleKey roleKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			roleMapper.deleteRole(roleKey);
			roleMapper.deleteDbFileByRole(roleKey);
			roleMapper.deleteDbTemplateByRole(roleKey);
			taskMapper.deleteTaskByRole(roleKey);
			taskMapper.deleteTaskDetailByRole(roleKey);
			taskMapper.deleteTaskConditionalByRole(roleKey);
			variableMapper.deleteDbRoleVariableByRole(roleKey);
			fileService.deleteRoleDir(roleKey);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
		
	}

	public void registFile(UploadForm form) {
		DbFile dbFile = createDbFile(form);
		roleMapper.insertDbFile(dbFile);
	}

	public void deleteFile(GeneralFileForm form){
		FileKey fileKey = new FileKey(form);
		fileKey.setFileName(form.getFileName());
		roleMapper.deleteDbFile(fileKey);
	}

	public void deleteTemplate(GeneralFileForm form){
		TemplateKey templateKey = new TemplateKey(form);
		templateKey.setTemplateName(form.getFileName());
		roleMapper.deleteDbTemplate(templateKey);
	}

	private DbFile createDbFile(UploadForm form) {
		DbFile dbFile = new DbFile(form);
		dbFile.setFileName(form.getFileName());
		return dbFile;
	}

	public void registTemplate(UploadForm form) {
		DbTemplate dbTemplate = createDbTemplate(form);
		roleMapper.insertDbTemplate(dbTemplate);
	}

	private DbTemplate createDbTemplate(UploadForm form) {
		DbTemplate dbTemplate = new DbTemplate(form);
		dbTemplate.setTemplateName(form.getFileName());
		return dbTemplate;
	}

	public List<DbFile> getDbFileList(RoleKey roleKey){
		return roleMapper.selectDbFileList(roleKey);
	}

	public List<DbTemplate> getDbTemplateList(RoleKey roleKey){
		return roleMapper.selectDbTemplateList(roleKey);
	}
}
