package jansible.web.project;

import java.util.List;

import jansible.file.JansibleFiler;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbServiceGroup;
import jansible.web.project.project.form.EnvironmentForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class EnvironmentService {
	@Autowired
	private EnvironmentMapper environmentMapper;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private VariableMapper variableMapper;
	
	@Autowired
	private JansibleFiler jansibleFiler;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public List<DbEnvironment> getEnvironmentList(ProjectKey projectKey){
		return environmentMapper.selectEnvironmentList(projectKey);
	}

	public void registEnvironment(EnvironmentForm form) {
		DbEnvironment dbEnvironment = new DbEnvironment(form);
		environmentMapper.insertEnvironment(dbEnvironment);;
	}

	public void deleteEnvironment(EnvironmentKey environmentKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			environmentMapper.deleteEnvironment(environmentKey);
			serviceGroupMapper.deleteServiceGroupByEnvironment(environmentKey);
			serviceGroupMapper.deleteDbRoleRelationByEnvironment(environmentKey);
			serviceGroupMapper.deleteDbServerRelationByEnvironment(environmentKey);
			variableMapper.deleteDbEnvironmentVariableByEnvironment(environmentKey);
			variableMapper.deleteDbServiceGroupVariableByEnvironment(environmentKey);
			List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(environmentKey);
			for (DbServiceGroup dbServiceGroup : dbServiceGroupList) {
				jansibleFiler.deleteGroupVariableYaml(dbServiceGroup);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}
}
