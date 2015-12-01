package jansible.web.project;

import jansible.file.Host;
import jansible.file.HostGroup;
import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.mapper.EnvironmentMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbEnvironment;
import jansible.model.database.DbServer;
import jansible.model.database.DbServerParameter;
import jansible.model.database.DbServerRelation;
import jansible.model.database.DbServiceGroup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostsFileService {
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private JansibleHostsDumper jansibleHostsDumper;
	@Autowired
	private EnvironmentMapper environmentMapper;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private ServerMapper serverMapper;
	
	public void outputServerHostsData(ProjectKey key){
		List<DbServer> serverList = serverMapper.selectServerList(key);
		for(DbServer server : serverList){
			List<HostGroup> hostGroupList = new ArrayList<>();
			List<DbServerRelation> dbServerRelationList = serviceGroupMapper.selectDbServerRelationListByServer(server);
			for(DbServerRelation dbServerRelation : dbServerRelationList){
				HostGroup hostGroup = createHostGroup(dbServerRelation, server);
				hostGroupList.add(hostGroup);
			}
			String hostsFileContent = jansibleHostsDumper.getString(hostGroupList);
			jansibleFiler.writeServerHostsFile(server, hostsFileContent);
		}
	}
	
	private HostGroup createHostGroup(ServiceGroupKey serviceGroupKey, ServerKey serverKey){
		HostGroup hostGroup = new HostGroup();
		hostGroup.setGroupName(jansibleFiler.getGroupName(serviceGroupKey));
		List<DbServerParameter> dbServerParameterList = serverMapper.selectServerParameterList(serverKey);
		hostGroup.addHost(createHost(serverKey.getServerName(), dbServerParameterList));
		return hostGroup;
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
}
