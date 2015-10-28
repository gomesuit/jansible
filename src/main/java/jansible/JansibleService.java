package jansible;

import java.util.ArrayList;
import java.util.List;

import jansible.data.JansibleMapper;
import jansible.model.database.DbChoice;
import jansible.model.database.DbModule;
import jansible.model.database.DbParameter;
import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.model.gethtml.Required;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JansibleService {
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

}
