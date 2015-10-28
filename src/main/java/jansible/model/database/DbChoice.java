package jansible.model.database;

public class DbChoice {
	private String moduleName;
	private String parameterName;
	private String choiceValue;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getChoiceValue() {
		return choiceValue;
	}
	public void setChoiceValue(String choiceValue) {
		this.choiceValue = choiceValue;
	}
}
