package jansible.web.project.form;

import jansible.model.common.InterfaceVariableKey;

public interface InterfaceVariable extends InterfaceVariableKey{
	public String getValue();
	public void setValue(String value);
}
