package jansible.web.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jansible.file.Host;
import jansible.file.HostGroup;
import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.mapper.ServiceGroupMapper;
import jansible.model.common.ServerRelationKey;
import jansible.model.database.DbRoleRelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private YamlService yamlService;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private JansibleHostsDumper jansibleHostsDumper;
	
	private static final String TEST_YAML_FILENAME = "TEST";

	public void outputTestYaml(String outputDir, ServerRelationKey key){
		String serverName = key.getServerName();
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(key);
		
		String yamlContent = yamlService.dumpStartYaml(serverName, dbRoleRelationList);
		
		ServerRelationKey testKey = new ServerRelationKey(key);
		testKey.setServerName(TEST_YAML_FILENAME);
		
		String filePath = outputDir + File.separator + TEST_YAML_FILENAME + ".yml";
		jansibleFiler.writeFile(filePath, yamlContent);
	}

	public void outputTestHostsData(String outputDir, ServerRelationKey key){
		List<HostGroup> hostGroupList = createHostGroupList(key);

		String hostsFileContent = jansibleHostsDumper.getString(hostGroupList);
		
		String filePath = outputDir + File.separator + TEST_YAML_FILENAME;
		jansibleFiler.writeFile(filePath, hostsFileContent);
	}

	private List<HostGroup> createHostGroupList(ServerRelationKey key){
		List<HostGroup> hostGroupList = new ArrayList<>();
		hostGroupList.add(createHostGroup(key));
		
		return hostGroupList;
	}

	private HostGroup createHostGroup(ServerRelationKey key){
		HostGroup hostGroup = new HostGroup();
		hostGroup.setGroupName(jansibleFiler.getGroupName(key));
		hostGroup.addHost(createHost(key.getServerName()));
		
		return hostGroup;
	}

	private Host createHost(String serverName) {
		Host host = new Host();
		host.setServerName(serverName);
		host.addParameter("ansible_connection", "local");
		
		return host;
	}
}
