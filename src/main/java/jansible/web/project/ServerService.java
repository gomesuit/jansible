package jansible.web.project;

import jansible.mapper.ServerMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerParameterKey;
import jansible.model.database.DbServer;
import jansible.model.database.DbServerParameter;
import jansible.web.project.server.ServerForm;
import jansible.web.project.server.ServerParameterForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ServerService {
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private FileService fileService;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;

	public List<DbServer> getServerList(ProjectKey projectKey){
		return serverMapper.selectServerList(projectKey);
	}
	
	public List<DbServer> getServerListByEnvironment(EnvironmentKey environmentKey){
		return serverMapper.selectServerListByEnvironment(environmentKey);
	}

	public void registServer(ServerForm form) {
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			DbServer dbServer = new DbServer(form);
			dbServer.setEnvironmentName(form.getEnvironmentName());
			serverMapper.insertServer(dbServer);
			
			fileService.outputHostsData(form);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void deleteServer(ServerKey serverKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			serverMapper.deleteServer(serverKey);
			serverMapper.deleteServerParameterByServer(serverKey);
			variableMapper.deleteDbServerVariableByServer(serverKey);
			fileService.deleteHostVariableYaml(serverKey);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}
	
	public List<DbServerParameter> getServerParameterList(ServerKey serverKey){
		return serverMapper.selectServerParameterList(serverKey);
	}

	public void registServerParameter(ServerParameterForm form) {
		DbServerParameter dbServerParameter = new DbServerParameter(form);
		dbServerParameter.setParameterValue(form.getParameterValue());
		serverMapper.insertServerParameter(dbServerParameter);
		
		fileService.outputHostsData(form);
	}

	public void deleteServerParameter(ServerParameterKey serverParameterKey){
		serverMapper.deleteServerParameter(serverParameterKey);

		fileService.outputHostsData(serverParameterKey);
	}
}
