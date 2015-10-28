package jansible.model.webform;

import java.util.List;

public class ModuleForm {
	private String moduleName;
	private String freeForm;
	private List<FormParameter> parameterList;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getFreeForm() {
		return freeForm;
	}
	public void setFreeForm(String freeForm) {
		this.freeForm = freeForm;
	}
	public List<FormParameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<FormParameter> parameterList) {
		this.parameterList = parameterList;
	}
}
