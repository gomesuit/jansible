package jansible.data;

import jansible.datamodel.DbChoice;
import jansible.datamodel.DbModule;
import jansible.datamodel.DbParameter;

public interface JansibleMapper {
	void insertModule(DbModule dbModule);
	void insertParameter(DbParameter dbParameter);
	void insertChoice(DbChoice dbChoice);
}
