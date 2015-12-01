package jansible.web.project;

import java.util.List;

import jansible.mapper.VariableMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.EnvironmentVariableKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.RoleVariableKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerVariableKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.ServiceGroupVariableKey;
import jansible.model.database.DbEnvironmentVariable;
import jansible.model.database.DbRoleVariable;
import jansible.model.database.DbServerVariable;
import jansible.model.database.DbServiceGroupVariable;
import jansible.web.project.environment.EnvironmentVariableForm;
import jansible.web.project.group.ServiceGroupVariableForm;
import jansible.web.project.role.RoleVariableForm;
import jansible.web.project.server.ServerVariableForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariableService {
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private FileService fileService;

	public void registRoleVariable(RoleVariableForm form) {
		DbRoleVariable dbRoleVariable = createDbRoleVariable(form);
		variableMapper.insertDbRoleVariable(dbRoleVariable);
		
		fileService.outputRoleVariableData(form);
	}

	public List<DbRoleVariable> getDbRoleVariableList(RoleKey roleKey){
		return variableMapper.selectDbRoleVariableList(roleKey);
	}

	public void deleteEnvironmentVariable(EnvironmentVariableKey environmentVariableKey){
		fileService.outputEnvironmentVariableData(environmentVariableKey);
		variableMapper.deleteDbEnvironmentVariable(environmentVariableKey);
	}

	public List<DbEnvironmentVariable> getDbEnvironmentVariableList(EnvironmentKey environmentKey){
		return variableMapper.selectDbEnvironmentVariableList(environmentKey);
	}

	public void deleteServiceGroupVariable(ServiceGroupVariableKey serviceGroupVariableKey){
		fileService.outputServiceGroupVariableData(serviceGroupVariableKey);
		variableMapper.deleteDbServiceGroupVariable(serviceGroupVariableKey);
	}

	public void deleteRoleVariable(RoleVariableKey roleVariableKey){
		variableMapper.deleteDbRoleVariable(roleVariableKey);
		fileService.outputRoleVariableData(roleVariableKey);
	}

	public List<DbServiceGroupVariable> getDbServiceGroupVariableList(ServiceGroupKey serviceGroupKey){
		return variableMapper.selectDbServiceGroupVariableList(serviceGroupKey);
	}

	public List<DbServerVariable> getDbServerVariableList(ServerKey serverKey){
		return variableMapper.selectDbServerVariableList(serverKey);
	}

	public void deleteServerVariable(ServerVariableKey serverVariableKey){
		fileService.outputServerVariableData(serverVariableKey);
		variableMapper.deleteDbServerVariable(serverVariableKey);
	}

	public void registServiceGroupVariable(ServiceGroupVariableForm form) {
		DbServiceGroupVariable dbServiceGroupVariable = createDbServiceGroupVariable(form);
		variableMapper.insertDbServiceGroupVariable(dbServiceGroupVariable);
		
		fileService.outputServiceGroupVariableData(form);
	}

	public void registServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = createDbServerVariable(form);
		variableMapper.insertDbServerVariable(dbServerVariable);
		
		fileService.outputServerVariableData(form);
	}

	public void registEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = createDbEnvironmentVariable(form);
		variableMapper.insertDbEnvironmentVariable(dbEnvironmentVariable);
		
		fileService.outputEnvironmentVariableData(form);
	}

	private DbRoleVariable createDbRoleVariable(RoleVariableForm form) {
		DbRoleVariable dbRoleVariable = new DbRoleVariable(form);
		dbRoleVariable.setValue(form.getValue());
		return dbRoleVariable;
	}

	private DbEnvironmentVariable createDbEnvironmentVariable(EnvironmentVariableForm form) {
		DbEnvironmentVariable dbEnvironmentVariable = new DbEnvironmentVariable(form);
		dbEnvironmentVariable.setValue(form.getValue());
		return dbEnvironmentVariable;
	}

	private DbServerVariable createDbServerVariable(ServerVariableForm form) {
		DbServerVariable dbServerVariable = new DbServerVariable(form);
		dbServerVariable.setValue(form.getValue());
		return dbServerVariable;
	}

	private DbServiceGroupVariable createDbServiceGroupVariable(ServiceGroupVariableForm form) {
		DbServiceGroupVariable dbServiceGroupVariable = new DbServiceGroupVariable(form);
		dbServiceGroupVariable.setValue(form.getValue());
		return dbServiceGroupVariable;
	}

	public List<String> getAllDbVariableNameList(ProjectKey projectKey){
		return variableMapper.selectAllDbVariableNameList(projectKey);
	}
}
