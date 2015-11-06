package jansible.model.database;

import jansible.model.common.InterfaceVariableKey;

public interface InterfaceDbVariable extends InterfaceVariableKey{
	public String getValue();
	public void setValue(String value);
}
