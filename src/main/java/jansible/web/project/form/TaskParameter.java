package jansible.web.project.form;

import java.util.List;

public class TaskParameter {
	private String parameterName;
	private String parameterValue;
	private String addedVersion;
	private boolean required;
	private String defaultValue;
	private List<String> choices;
	private String description;
	
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getAddedVersion() {
		return addedVersion;
	}
	public void setAddedVersion(String addedVersion) {
		this.addedVersion = addedVersion;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<String> getChoices() {
		return choices;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
