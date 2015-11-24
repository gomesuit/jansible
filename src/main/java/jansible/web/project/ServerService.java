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
import org.springframework.stereotype.Service;

@Service
public class ServerService {
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private FileService fileService;

	public List<DbServer> getServerList(ProjectKey projectKey){
		return serverMapper.selectServerList(projectKey);
	}
	
	public List<DbServer> getServerListByEnvironment(EnvironmentKey environmentKey){
		return serverMapper.selectServerListByEnvironment(environmentKey);
	}

	public void registServer(ServerForm form) {
		DbServer dbServer = new DbServer(form);
		dbServer.setEnvironmentName(form.getEnvironmentName());
		serverMapper.insertServer(dbServer);
		
		fileService.outputHostsData(form);
	}

	public void deleteServer(ServerKey serverKey){
		serverMapper.deleteServer(serverKey);
		serverMapper.deleteServerParameterByServer(serverKey);
		variableMapper.deleteDbServerVariableByServer(serverKey);
		fileService.deleteHostVariableYaml(serverKey);
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
