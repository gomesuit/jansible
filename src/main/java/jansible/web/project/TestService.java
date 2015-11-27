package jansible.web.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jansible.file.Host;
import jansible.file.HostGroup;
import jansible.file.JansibleFiler;
import jansible.file.JansibleHostsDumper;
import jansible.mapper.ServiceGroupMapper;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.database.DbRoleRelation;
import jansible.zip.JansibleZip;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private YamlService yamlService;
	@Autowired
	private VagrantService vagrantService;
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private JansibleHostsDumper jansibleHostsDumper;
	@Autowired
	private FileService fileService;
	
	private static final String TEST_YAML_FILENAME = "TEST";
	private static final String TEMP_PATH_PREFIX = "temp";
	
	public File getTestZipFile(ServerRelationKey key) throws Exception{
		fileService.reOutputAllData(key);
		
		File srcDir = new File(jansibleFiler.getProjectDirName(key));
		String tempDir = getTempDirPathName(key);
    	File destDir = new File(tempDir, key.getProjectName());
    	
    	FileUtils.copyDirectory(srcDir, destDir);
    	
    	vagrantService.saveVagrantFile(destDir.getPath());
    	outputTestYaml(destDir.getPath(), key);
    	outputTestHostsData(destDir.getPath(), key);

    	File zipfile = new File(tempDir, key.getProjectName() + ".zip");
    	JansibleZip.zip(zipfile, destDir);
    	
    	return zipfile;
	}
	
	public String getTempDirPathName(ProjectKey key){
		String systemRootDir = jansibleFiler.getSystemDirName();
    	String uuid = UUID.randomUUID().toString();
    	String tempDirPathName = systemRootDir + File.separator + TEMP_PATH_PREFIX + File.separator + uuid;
    	
		return tempDirPathName;
	}

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
