package jansible.web.manager.module.form;

import java.util.List;

public class ModuleForm {
	private String moduleName;
	private List<FormParameter> parameterList;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<FormParameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<FormParameter> parameterList) {
		this.parameterList = parameterList;
	}
}
