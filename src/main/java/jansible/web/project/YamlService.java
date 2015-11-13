package jansible.web.project;

import jansible.mapper.TaskMapper;
import jansible.model.database.DbTask;
import jansible.model.database.DbTaskConditional;
import jansible.model.database.DbTaskDetail;
import jansible.model.database.InterfaceDbVariable;
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
public class YamlService {
	@Autowired
	private TaskMapper taskMapper;

	public List<YamlModule> createYamlModuleList(List<DbTask> dbTaskList){
		List<YamlModule> modules = new ArrayList<>();
		for(DbTask dbTask : dbTaskList){
			List<DbTaskDetail> dbTaskDetailList = taskMapper.selectTaskDetailList(dbTask);
			YamlParameters yamlParameters = createParameters(dbTaskDetailList); 
			YamlModule yamlModule = new YamlModule(dbTask.getModuleName(), yamlParameters);
			yamlModule.setDescription(dbTask.getDescription());
			for(DbTaskConditional dbTaskConditional : taskMapper.selectDbTaskConditionalList(dbTask)){
				yamlModule.addConditional(dbTaskConditional.getConditionalName(), dbTaskConditional.getConditionalValue());
			}
			modules.add(yamlModule);
		}
		return modules;
	}

	public YamlParameters createParameters(List<DbTaskDetail> dbTaskDetailList) {
		YamlParameters yamlParameters = new YamlParameters();
		for(DbTaskDetail dbTaskDetail : dbTaskDetailList){
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
}
