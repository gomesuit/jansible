package jansible.form;

import java.util.List;

public class ModuleForm {
	private String moduleName;
	private String freeForm;
	private List<FormParameter> parameter;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<FormParameter> getParameter() {
		return parameter;
	}
	public void setParameter(List<FormParameter> parameter) {
		this.parameter = parameter;
	}
	public String getFreeForm() {
		return freeForm;
	}
	public void setFreeForm(String freeForm) {
		this.freeForm = freeForm;
	}
	@Override
	public String toString() {
		return "Test [moduleName=" + moduleName + ", freeForm=" + freeForm
				+ ", parameter=" + parameter + "]";
	}
}
