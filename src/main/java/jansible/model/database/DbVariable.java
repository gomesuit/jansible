package jansible.model.database;

import jansible.model.common.Target;
import jansible.model.common.VariableKey;

public class DbVariable extends VariableKey{
	private String value;
	
	public DbVariable(){}

	public DbVariable(String projectName, Target target, String targetName, String variableName) {
		super(projectName, target, targetName, variableName);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
