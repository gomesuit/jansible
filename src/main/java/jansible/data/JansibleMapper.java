package jansible.data;

import java.util.List;

import jansible.datamodel.DbChoice;
import jansible.datamodel.DbModule;
import jansible.datamodel.DbParameter;

public interface JansibleMapper {
	void insertModule(DbModule dbModule);
	void insertParameter(DbParameter dbParameter);
	void insertChoice(DbChoice dbChoice);
	
	DbModule selectModule(String moduleName);
	List<DbParameter> selectParameterList(String moduleName);
	List<DbChoice> selectChoiceList(DbParameter dbParameter);
}
