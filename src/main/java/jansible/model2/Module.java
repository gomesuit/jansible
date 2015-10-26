package jansible.model2;

import java.util.List;

public class Module {
	private String name;
	private String description;
	private List<Parameter> parameterList;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Parameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
	@Override
	public String toString() {
		return "Module [name=" + name + ", description=" + description
				+ ", parameterList=" + parameterList + "]";
	}
}
