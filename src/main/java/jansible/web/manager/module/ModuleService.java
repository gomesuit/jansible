package jansible.web.manager.module;

import java.util.ArrayList;
import java.util.List;

import jansible.mapper.JansibleMapper;
import jansible.model.common.Required;
import jansible.model.database.DbChoice;
import jansible.model.database.DbModule;
import jansible.model.database.DbParameter;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;

import org.eclipse.jgit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {
	@Autowired
	private JansibleMapper jansibleMapper;
	
	public void insertModule(DbModule dbModule){
		jansibleMapper.insertModule(dbModule);
	}
	
	public void insertModule(HtmlModule module){
		DbModule dbModule = new DbModule();
		dbModule.setModuleName(module.getName());
		dbModule.setDescription(module.getDescription());
		insertModule(dbModule);
		insertParameterList(module);
	}
	
	private void insertParameterList(HtmlModule module){
		List<HtmlParameter> parameterList = module.getParameterList();
		for(HtmlParameter parameter : parameterList){
			DbParameter dbParameter = new DbParameter();
			dbParameter.setModuleName(module.getName());
			dbParameter.setParameterName(parameter.getName());
			dbParameter.setAddedVersion(parameter.getAddedVersion());
			dbParameter.setRequired(Required.getRequired(parameter.isRequired()));
			dbParameter.setAddedVersion(parameter.getAddedVersion());
			dbParameter.setDefautlValue(parameter.getDefaultValue());
			dbParameter.setDescription(parameter.getDescription());
			dbParameter.setFreeForm(parameter.isFreeForm());
			insertParameter(dbParameter);
			insertChoices(module, parameter);
		}
	}
	
	private void insertChoices(HtmlModule module, HtmlParameter parameter){
		for(String choice : parameter.getChoices()){
			DbChoice dbChoice = new DbChoice();
			dbChoice.setModuleName(module.getName());
			dbChoice.setParameterName(parameter.getName());
			dbChoice.setChoiceValue(choice);
			insertChoice(dbChoice);
		}
	}

	public void insertParameter(DbParameter dbParameter){
		jansibleMapper.insertParameter(dbParameter);
	}

	public void insertChoice(DbChoice dbChoice){
		jansibleMapper.insertChoice(dbChoice);
	}
	
	public HtmlModule getModule(String moduleName){
		DbModule dbModule = jansibleMapper.selectModule(moduleName);
		HtmlModule module = new HtmlModule();
		module.setName(dbModule.getModuleName());
		module.setDescription(dbModule.getDescription());
		module.setParameterList(getParameterList(moduleName));
		return module;
	}
	
	public List<String> getModuleNameList(){
		List<String> moduleNameList = jansibleMapper.selectModuleNameList();
		return moduleNameList;
	}
	
	public List<String> getAvailableModuleList(){
		return jansibleMapper.selectAvailableModule();
	}
	
	public List<ModuleRow> getModuleList(){
		List<String> moduleNameList = jansibleMapper.selectModuleNameList();
		List<String> availableModuleNameList = jansibleMapper.selectAvailableModule();
		
		List<ModuleRow> moduleRowList = new ArrayList<>();
		for(String moduleName : moduleNameList){
			ModuleRow moduleRow = new ModuleRow();
			moduleRow.setModuleName(moduleName);
			if(availableModuleNameList.contains(moduleName)){
				moduleRow.setActive(true);
			}else{
				moduleRow.setActive(false);
			}
			moduleRowList.add(moduleRow);
		}
		
		return moduleRowList;
	}

	private List<HtmlParameter> getParameterList(String moduleName) {
		List<DbParameter> dbParameterList = jansibleMapper.selectParameterList(moduleName);
		List<HtmlParameter> parameterList = new ArrayList<>();
		for(DbParameter dbParameter : dbParameterList){
			HtmlParameter parameter = new HtmlParameter();
			parameter.setName(dbParameter.getParameterName());
			parameter.setAddedVersion(dbParameter.getAddedVersion());
			parameter.setRequired(dbParameter.getRequired().isRequired());
			parameter.setDefaultValue(dbParameter.getDefautlValue());
			parameter.setDescription(dbParameter.getDescription());
			parameter.setFreeForm(dbParameter.isFreeForm());
			parameter.setChoices(getChoices(dbParameter));
			parameterList.add(parameter);
		}
		return parameterList;
	}

	private List<String> getChoices(DbParameter dbParameter) {
		List<String> choices = new ArrayList<>();
		List<DbChoice> dbChoiceList = jansibleMapper.selectChoiceList(dbParameter);
		for(DbChoice dbChoice : dbChoiceList){
			choices.add(dbChoice.getChoiceValue());
		}
		return choices;
	}
	
	public void registAvailableModuleList(List<ModuleRow> moduleRowList){
		List<String> oldModuleNameList = jansibleMapper.selectAvailableModule();
		List<String> newModuleNameList = getModuleNameList(moduleRowList);
				
		for(String moduleName : newModuleNameList){
			if(StringUtils.isEmptyOrNull(moduleName)) continue;
			
			if(!oldModuleNameList.contains(moduleName)){
				jansibleMapper.insertAvailableModule(moduleName);
			}
		}
		for(String moduleName : oldModuleNameList){
			if(StringUtils.isEmptyOrNull(moduleName)) continue;
			
			if(!newModuleNameList.contains(moduleName)){
				jansibleMapper.deleteAvailableModule(moduleName);
			}
		}
	}
	
	private List<String> getModuleNameList(List<ModuleRow> moduleRowList){
		List<String> moduleNameList = new ArrayList<>();
		
		for(ModuleRow moduleRow : moduleRowList){
			if(moduleRow.isActive()){
				moduleNameList.add(moduleRow.getModuleName());
			}
		}
		
		return moduleNameList;
	}

}
