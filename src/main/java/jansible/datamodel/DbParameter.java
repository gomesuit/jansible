package jansible.datamodel;

import jansible.model2.Required;

public class DbParameter {
	private String moduleName;
	private String parameterName;
	private Required required;
	private String addedVersion;
	private String defautlValue;
	private String description;
	private boolean freeForm;
	
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
	public Required getRequired() {
		return required;
	}
	public void setRequired(Required required) {
		this.required = required;
	}
	public String getAddedVersion() {
		return addedVersion;
	}
	public void setAddedVersion(String addedVersion) {
		this.addedVersion = addedVersion;
	}
	public String getDefautlValue() {
		return defautlValue;
	}
	public void setDefautlValue(String defautlValue) {
		this.defautlValue = defautlValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isFreeForm() {
		return freeForm;
	}
	public void setFreeForm(boolean freeForm) {
		this.freeForm = freeForm;
	}
}
