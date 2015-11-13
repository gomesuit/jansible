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
import jansible.web.project.project.EnvironmentForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<DbEnvironment> getEnvironmentList(ProjectKey projectKey){
		return environmentMapper.selectEnvironmentList(projectKey);
	}

	public void registEnvironment(EnvironmentForm form) {
		DbEnvironment dbEnvironment = new DbEnvironment(form);
		environmentMapper.insertEnvironment(dbEnvironment);;
	}

	public void deleteEnvironment(EnvironmentKey environmentKey){
		environmentMapper.deleteEnvironment(environmentKey);
		serviceGroupMapper.deleteServiceGroupByEnvironment(environmentKey);
		serviceGroupMapper.deleteDbRoleRelationByEnvironment(environmentKey);
		serverMapper.deleteServerByEnvironment(environmentKey);
		variableMapper.deleteDbEnvironmentVariableByEnvironment(environmentKey);
		variableMapper.deleteDbServerVariableByEnvironment(environmentKey);
		variableMapper.deleteDbServiceGroupVariableByEnvironment(environmentKey);
		
		List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(environmentKey);
		for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
			jansibleFiler.deleteGroupVariableYaml(dbServiceGroup);
		}
	}
}
