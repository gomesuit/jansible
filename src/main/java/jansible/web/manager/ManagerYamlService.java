package jansible.web.manager;

import jansible.mapper.GlobalTaskMapper;
import jansible.model.database.DbGlobalTask;
import jansible.model.database.DbGlobalTaskConditional;
import jansible.model.database.DbGlobalTaskDetail;
import jansible.model.database.InterfaceDbVariable;
import jansible.model.yamldump.YamlDumper;
import jansible.model.yamldump.YamlModule;
import jansible.model.yamldump.YamlParameter;
import jansible.model.yamldump.YamlParameters;
import jansible.model.yamldump.YamlVariable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerYamlService {
	@Autowired
	private GlobalTaskMapper taskMapper;
	@Autowired
	private YamlDumper yamlDumper;

	private List<YamlModule> createYamlModuleList(List<DbGlobalTask> dbGlobalTaskList){
		List<YamlModule> modules = new ArrayList<>();
		for(DbGlobalTask dbTask : dbGlobalTaskList){
			List<DbGlobalTaskDetail> dbTaskDetailList = taskMapper.selectTaskDetailList(dbTask);
			YamlParameters yamlParameters = createParameters(dbTaskDetailList); 
			YamlModule yamlModule = new YamlModule(dbTask.getModuleName(), yamlParameters);
			yamlModule.setDescription(dbTask.getDescription());
			for(DbGlobalTaskConditional dbTaskConditional : taskMapper.selectDbTaskConditionalList(dbTask)){
				yamlModule.addConditional(dbTaskConditional.getConditionalName(), dbTaskConditional.getConditionalValue());
			}
			modules.add(yamlModule);
		}
		return modules;
	}
	
	public String createYaml(List<DbGlobalTask> dbGlobalTaskList){
		List<YamlModule> modules = createYamlModuleList(dbGlobalTaskList);
		return yamlDumper.dump(modules);
	}

	private YamlParameters createParameters(List<DbGlobalTaskDetail> dbTaskDetailList) {
		YamlParameters yamlParameters = new YamlParameters();
		for(DbGlobalTaskDetail dbTaskDetail : dbTaskDetailList){
			if(StringUtils.isBlank(dbTaskDetail.getParameterValue())){
				continue;
			}
			YamlParameter YamlParameter = new YamlParameter(dbTaskDetail.getParameterName(), dbTaskDetail.getParameterValue());
			yamlParameters.addParameter(YamlParameter);
		}
		return yamlParameters;
	}

	public <T extends InterfaceDbVariable> List<YamlVariable> createYamlVariableList(List<T> dbVariableList){
		List<YamlVariable> yamlVariableList = new ArrayList<>();
		for(InterfaceDbVariable dbVariable : dbVariableList){
			yamlVariableList.add(new YamlVariable(dbVariable.getVariableName(), dbVariable.getValue()));
		}
		return yamlVariableList;
	}
	
	public String dumpVariable(List<YamlVariable> yamlVariableList){
		return yamlDumper.dumpVariable(yamlVariableList);
	}
	
	public String createTaskPreview(DbGlobalTask dbTask){
		List<DbGlobalTask> dbGlobalTaskList = new ArrayList<>();
		dbGlobalTaskList.add(dbTask);
		List<YamlModule> modules = createYamlModuleList(dbGlobalTaskList);
		return yamlDumper.dump(modules).replaceAll("\n", "<br />");
	}
	
	public String createTaskParametersValue(String moduleName, List<DbGlobalTaskDetail> dbTaskDetailList){
		YamlModule yamlModule = new YamlModule(moduleName, createParameters(dbTaskDetailList));
		return yamlModule.getParameters().toString();
	}
}
