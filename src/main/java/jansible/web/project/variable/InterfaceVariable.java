package jansible.web.project.variable;

import jansible.model.common.InterfaceVariableKey;

public interface InterfaceVariable extends InterfaceVariableKey{
	public String getValue();
	public void setValue(String value);
}
