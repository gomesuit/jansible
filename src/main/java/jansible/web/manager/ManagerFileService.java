package jansible.web.manager;

import jansible.file.JansibleFiler;
import jansible.mapper.GlobalRoleMapper;
import jansible.mapper.GlobalTaskMapper;
import jansible.model.common.GlobalRoleKey;
import jansible.model.common.RoleKey;
import jansible.model.database.DbGlobalRoleVariable;
import jansible.model.database.DbGlobalTask;
import jansible.model.yamldump.YamlVariable;
import jansible.web.manager.role.GitForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerFileService {
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private GlobalRoleMapper roleMapper;
	@Autowired
	private GlobalTaskMapper taskMapper;
	@Autowired
	private ManagerYamlService yamlService;
	
	public void deleteRoleDir(RoleKey roleKey){
		jansibleFiler.deleteRoleDir(roleKey);
	}

	public void outputRoleVariableData(GlobalRoleKey key){
		List<DbGlobalRoleVariable> dbRoleVariableList = roleMapper.selectDbRoleVariableList(key);
		List<YamlVariable> yamlVariableList = yamlService.createYamlVariableList(dbRoleVariableList);
		
		if(!yamlVariableList.isEmpty()){
			String yamlContent = yamlService.dumpVariable(yamlVariableList);
			jansibleFiler.writeGlobalRoleVariableYaml(key, yamlContent);
		}
	}

	public void outputRoleData(GlobalRoleKey key){
		jansibleFiler.mkGlobalRoleDir(key);
		jansibleFiler.mkGlobalRoleTaskDir(key);
		jansibleFiler.mkGlobalRoleTemplateDir(key);
		jansibleFiler.mkGlobalRoleFileDir(key);
		jansibleFiler.mkGlobalRoleVariableDir(key);
		jansibleFiler.writeGlobalRoleYaml(key);
	}

	public void outputTaskData(GlobalRoleKey key){
		List<DbGlobalTask> dbGlobalTaskList = taskMapper.selectTaskList(key);
		jansibleFiler.writeGlobalRoleYaml(key, yamlService.createYaml(dbGlobalTaskList));
	}

	public void deleteRoleDir(GlobalRoleKey key) {
		jansibleFiler.deleteGlobelRoleDir(key);
	}

	public void reOutputAllData(GitForm form) {
		outputRoleData(form);
		outputRoleVariableData(form);
		outputTaskData(form);
	}
}
