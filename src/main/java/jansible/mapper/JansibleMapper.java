package jansible.mapper;

import java.util.List;

import jansible.model.database.DbChoice;
import jansible.model.database.DbModule;
import jansible.model.database.DbParameter;

public interface JansibleMapper {
	void insertModule(DbModule dbModule);
	void insertParameter(DbParameter dbParameter);
	void insertChoice(DbChoice dbChoice);
	
	DbModule selectModule(String moduleName);
	List<DbParameter> selectParameterList(String moduleName);
	List<DbChoice> selectChoiceList(DbParameter dbParameter);
	List<String> selectModuleNameList();
}
