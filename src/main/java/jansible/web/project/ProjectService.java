package jansible.web.project;

import java.util.List;

import jansible.mapper.ApplyHistoryMapper;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.GlobalRoleRelationMapper;
import jansible.mapper.ProjectMapper;
import jansible.mapper.RoleMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.TaskMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbProject;
import jansible.web.project.project.JenkinsInfoForm;
import jansible.web.project.top.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private EnvironmentMapper environmentMapper;
	@Autowired
	private ApplyHistoryMapper applyHistoryMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private GlobalRoleRelationMapper globalRoleRelationMapper;
	
	@Autowired
	private GitService gitService;
	@Autowired
	private FileService fileService;

	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	public void registJenkinsInfo(JenkinsInfoForm form){
		DbProject dbProject = new DbProject(form);
		dbProject.setJenkinsIpAddress(form.getJenkinsIpAddress());
		dbProject.setJenkinsPort(form.getJenkinsPort());
		dbProject.setJenkinsJobName(form.getJenkinsJobName());
		projectMapper.updateJenkinsInfo(dbProject);
	}

	public void registProject(ProjectForm form) throws Exception {
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			DbProject dbProject = new DbProject(form, form.getRepositoryUrl());
			projectMapper.insertProject(dbProject);
			gitService.cloneRepository(form);
			fileService.outputProjectData(dbProject);
		} catch (Exception e) {
			transactionManager.rollback(status);
			fileService.deleteProjectDir(form);
			throw e;
		}
		transactionManager.commit(status);
	}

	public List<DbProject> getProjectList(){
		return projectMapper.selectProjectList();
	}

	public DbProject getProject(ProjectKey projectKey){
		return projectMapper.selectProject(projectKey);
	}
	
	public void deleteProject(ProjectKey projectKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			projectMapper.deleteProject(projectKey);
			environmentMapper.deleteEnvironmentByProject(projectKey);
			applyHistoryMapper.deleteApplyHistoryByProject(projectKey);
			roleMapper.deleteDbFileByProject(projectKey);
			roleMapper.deleteDbTemplateByProject(projectKey);
			roleMapper.deleteRoleByProject(projectKey);
			serverMapper.deleteServerByProject(projectKey);
			serverMapper.deleteServerParameterByProject(projectKey);
			serviceGroupMapper.deleteDbRoleRelationByProject(projectKey);
			serviceGroupMapper.deleteDbServerRelationByProject(projectKey);
			serviceGroupMapper.deleteServiceGroupByProject(projectKey);
			taskMapper.deleteTaskByProject(projectKey);
			taskMapper.deleteTaskDetailByProject(projectKey);
			variableMapper.deleteDbEnvironmentVariableByProject(projectKey);
			variableMapper.deleteDbRoleVariableByProject(projectKey);
			variableMapper.deleteDbServerVariableByProject(projectKey);
			variableMapper.deleteDbServiceGroupVariableByProject(projectKey);
			globalRoleRelationMapper.deleteRoleRelationByProject(projectKey);
			fileService.deleteProjectDir(projectKey);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}
}
