package jansible.web.project;

import jansible.file.JansibleFiler;
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
import jansible.web.project.project.RoleForm;
import jansible.web.project.role.GeneralFileForm;
import jansible.web.project.role.UploadForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private JansibleFiler jansibleFiler;

	public void registRole(RoleForm form) {
		DbRole dbRole = new DbRole(form);
		roleMapper.insertRole(dbRole);
		fileService.outputRoleData(dbRole);
	}

	public List<DbRole> getRoleList(ProjectKey projectKey){
		return roleMapper.selectRoleList(projectKey);
	}

	public void deleteRole(RoleKey roleKey){
		roleMapper.deleteRole(roleKey);
		roleMapper.deleteDbFileByRole(roleKey);
		roleMapper.deleteDbTemplateByRole(roleKey);
		taskMapper.deleteTaskByRole(roleKey);
		taskMapper.deleteTaskDetailByRole(roleKey);
		taskMapper.deleteTaskConditionalByRole(roleKey);
		variableMapper.deleteDbRoleVariableByRole(roleKey);
		jansibleFiler.deleteRoleDir(roleKey);
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
