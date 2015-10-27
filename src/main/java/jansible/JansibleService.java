package jansible;

import java.util.List;

import jansible.data.JansibleMapper;
import jansible.datamodel.DbChoice;
import jansible.datamodel.DbModule;
import jansible.datamodel.DbParameter;
import jansible.model2.Module;
import jansible.model2.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JansibleService {
	@Autowired
	private JansibleMapper jansibleMapper;
	
	public void insertModule(DbModule dbModule){
		jansibleMapper.insertModule(dbModule);
	}
	
	public void insertModule(Module module){
		DbModule dbModule = new DbModule();
		dbModule.setModuleName(module.getName());
		dbModule.setDescription(module.getDescription());
		insertModule(dbModule);
		insertParameterList(module);
	}
	
	private void insertParameterList(Module module){
		List<Parameter> parameterList = module.getParameterList();
		for(Parameter parameter : parameterList){
			DbParameter dbParameter = new DbParameter();
			dbParameter.setModuleName(module.getName());
			dbParameter.setParameterName(parameter.getName());
			dbParameter.setAddedVersion(parameter.getAddedVersion());
			if(parameter.isRequired()){
				dbParameter.setRequired(DbParameter.Required.yes);
			}else{
				dbParameter.setRequired(DbParameter.Required.no);
			}
			dbParameter.setAddedVersion(parameter.getAddedVersion());
			dbParameter.setDefautlValue(parameter.getDefaultValue());
			dbParameter.setDescription(parameter.getDescription());
			if(parameter.isFreeForm()){
				dbParameter.setIsFreeForm(DbParameter.IsFreeForm.True);
			}else{
				dbParameter.setIsFreeForm(DbParameter.IsFreeForm.False);
			}
			insertParameter(dbParameter);
			insertChoices(module, parameter);
		}
	}
	
	private void insertChoices(Module module, Parameter parameter){
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

}
