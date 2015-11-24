package jansible.web.project;

import jansible.file.Host;
import jansible.file.HostGroup;
import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.TaskMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.common.TaskKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbEnvironmentVariable;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbRoleVariable;
import jansible.model.database.DbServer;
import jansible.model.database.DbServerParameter;
import jansible.model.database.DbServerRelation;
import jansible.model.database.DbServerVariable;
import jansible.model.database.DbServiceGroup;
import jansible.model.database.DbServiceGroupVariable;
import jansible.model.database.DbTask;
import jansible.model.yamldump.YamlVariable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private JansibleHostsDumper jansibleHostsDumper;
	@Autowired
	private EnvironmentMapper environmentMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private YamlService yamlService;
	
	
	public void deleteHostVariableYaml(ServerKey serverKey){
		jansibleFiler.deleteHostVariableYaml(serverKey);
	}
	
	public void deleteGroupVariableYaml(ServiceGroupKey serviceGroupKey){
		jansibleFiler.deleteGroupVariableYaml(serviceGroupKey);
	}
	
	public void deleteRoleDir(RoleKey roleKey){
		jansibleFiler.deleteRoleDir(roleKey);
	}

	public void outputProjectData(ProjectKey projectKey){
		jansibleFiler.mkHostVariableDir(projectKey);
		jansibleFiler.mkGroupVariableDir(projectKey);
	}

	public void outputRoleVariableData(RoleKey roleKey){
		List<DbRoleVariable> dbRoleVariableList = variableMapper.selectDbRoleVariableList(roleKey);
		List<YamlVariable> yamlVariableList = yamlService.createYamlVariableList(dbRoleVariableList);
		
		if(!yamlVariableList.isEmpty()){
			String yamlContent = yamlService.dumpVariable(yamlVariableList);
			jansibleFiler.writeRoleVariableYaml(roleKey, yamlContent);
		}
	}

	public void outputEnvironmentVariableData(EnvironmentKey environmentKey){
		List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(environmentKey);
		
		for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
			outputServiceGroupVariableData(dbServiceGroup);
		}
	}

	public void outputServiceGroupVariableData(ServiceGroupKey serviceGroupKey){
		List<DbEnvironmentVariable> dbEnvironmentVariableList = variableMapper.selectDbEnvironmentVariableList(serviceGroupKey);
		List<YamlVariable> envYamlVariableList = yamlService.createYamlVariableList(dbEnvironmentVariableList);
		
		List<DbServiceGroupVariable> dbServiceGroupVariableList = variableMapper.selectDbServiceGroupVariableList(serviceGroupKey);
		List<YamlVariable> groupVamlVariableList = yamlService.createYamlVariableList(dbServiceGroupVariableList);
		
		envYamlVariableList.addAll(groupVamlVariableList);
		
		if(!envYamlVariableList.isEmpty()){
			String yamlContent = yamlService.dumpVariable(envYamlVariableList);
			jansibleFiler.writeGroupVariableYaml(serviceGroupKey, yamlContent);
		}
	}

	public void outputServerVariableData(ServerKey serverKey){
		List<DbServerVariable> dbServerVariableList = variableMapper.selectDbServerVariableList(serverKey);
		List<YamlVariable> yamlVariableList = yamlService.createYamlVariableList(dbServerVariableList);
		
		if(!yamlVariableList.isEmpty()){
			String yamlContent = yamlService.dumpVariable(yamlVariableList);
			jansibleFiler.writeHostVariableYaml(serverKey, yamlContent);
		}
	}

	public void outputRoleData(RoleKey roleKey){
		jansibleFiler.mkRoleDir(roleKey);
		jansibleFiler.mkRoleTaskDir(roleKey);
		jansibleFiler.mkRoleTemplateDir(roleKey);
		jansibleFiler.mkRoleFileDir(roleKey);
		jansibleFiler.mkRoleVariableDir(roleKey);
		jansibleFiler.writeRoleYaml(roleKey);
	}

	public void outputRoleRelationData(ServiceGroupKey serviceGroupKey){
		String groupName = jansibleFiler.getGroupName(serviceGroupKey);
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(serviceGroupKey);
		
		String yamlContent = yamlService.dumpStartYaml(groupName, dbRoleRelationList);
		jansibleFiler.writeStartYaml(serviceGroupKey, yamlContent);
	}

	public void outputServerRelationData(ServerRelationKey key){
		String groupName = key.getServerName();
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(key);
		
		String yamlContent = yamlService.dumpStartYaml(groupName, dbRoleRelationList);
		jansibleFiler.writeServerStartYaml(key, yamlContent);
	}

	public void outputTaskData(TaskKey taskKey){
		List<DbTask> dbTaskList = taskMapper.selectTaskList(taskKey);
		jansibleFiler.writeRoleYaml(taskKey, yamlService.createYaml(dbTaskList));
	}

	public void outputHostsData(ProjectKey projectKey){
		List<HostGroup> hostGroupList = createHostGroupList(projectKey);
		
		if(!hostGroupList.isEmpty()){
			String hostsFileContent = jansibleHostsDumper.getString(hostGroupList);
			jansibleFiler.writeHostsFile(projectKey, hostsFileContent);
		}
	}

	private List<HostGroup> createHostGroupList(ProjectKey projectKey){
		List<HostGroup> hostGroupList = new ArrayList<>();
		
		List<DbEnvironment> dbEnvironmentList = environmentMapper.selectEnvironmentList(projectKey);
		for(DbEnvironment dbEnvironment : dbEnvironmentList){
			List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(dbEnvironment);
			for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
				HostGroup hostGroup = createHostGroup(dbServiceGroup);
				hostGroupList.add(hostGroup);
			}
		}
		return hostGroupList;
	}

	private HostGroup createHostGroup(ServiceGroupKey serviceGroupKey){
		HostGroup hostGroup = new HostGroup();
		hostGroup.setGroupName(jansibleFiler.getGroupName(serviceGroupKey));
		List<DbServer> dbServerList = serverMapper.selectServerListByServiceGroup(serviceGroupKey);
		for(DbServer server : dbServerList){
			List<DbServerParameter> dbServerParameterList = serverMapper.selectServerParameterList(server);
			hostGroup.addHost(createHost(server.getServerName(), dbServerParameterList));
		}
		return hostGroup;
	}

	private Host createHost(String serverName, List<DbServerParameter> dbServerParameterList) {
		Host host = new Host();
		host.setServerName(serverName);
		
		for(DbServerParameter dbServerParameter : dbServerParameterList){
			host.addParameter(dbServerParameter.getParameterName(), dbServerParameter.getParameterValue());
		}
		
		return host;
	}

	public void reOutputAllData(ProjectKey projectKey){
		jansibleFiler.deleteAllStartYamlfile(projectKey);
		jansibleFiler.deleteGroupVariableDir(projectKey);
		jansibleFiler.deleteHostVariableDir(projectKey);
		
		outputProjectData(projectKey);
		outputHostsData(projectKey);
		List<DbEnvironment> dbEnvironmentList = environmentMapper.selectEnvironmentList(projectKey);
		for(DbEnvironment dbEnvironment : dbEnvironmentList){
			List<DbServiceGroup> dbServiceGroupList = serviceGroupMapper.selectServiceGroupList(dbEnvironment);
			for(DbServiceGroup dbServiceGroup : dbServiceGroupList){
				outputServiceGroupVariableData(dbServiceGroup);
				outputRoleRelationData(dbServiceGroup);
				List<DbServerRelation> dbServerRelationList = serviceGroupMapper.selectDbServerRelationList(dbServiceGroup);
				for(DbServerRelation dbServerRelation : dbServerRelationList){
					outputServerRelationData(dbServerRelation);
				}
			}
		}
		List<DbServer> dbServerList = serverMapper.selectServerList(projectKey);
		for(DbServer dbServer : dbServerList){
			outputServerVariableData(dbServer);
		}
	}
}
