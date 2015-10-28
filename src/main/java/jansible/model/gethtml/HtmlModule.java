package jansible.model.gethtml;

import java.util.List;

public class HtmlModule {
	private String name;
	private String description;
	private List<HtmlParameter> parameterList;

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
	public List<HtmlParameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<HtmlParameter> parameterList) {
		this.parameterList = parameterList;
	}
	@Override
	public String toString() {
		return "Module [name=" + name + ", description=" + description
				+ ", parameterList=" + parameterList + "]";
	}
}
