package jansible.model.gethtml;

import java.util.List;

public class HtmlParameter {
	private String name;
	private String addedVersion;
	private boolean required;
	private String defaultValue;
	private List<String> choices;
	private String description;
	private boolean isFreeForm;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAddedVersion() {
		return addedVersion;
	}
	public void setAddedVersion(String addedVersion) {
		this.addedVersion = addedVersion;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isFreeForm() {
		return isFreeForm;
	}
	public void setFreeForm(boolean isFreeForm) {
		this.isFreeForm = isFreeForm;
	}
	@Override
	public String toString() {
		return "Parameter [name=" + name + ", addedVersion=" + addedVersion
				+ ", required=" + required + ", defaultValue=" + defaultValue
				+ ", choices=" + choices + ", description=" + description
				+ ", isFreeForm=" + isFreeForm + "]";
	}
}
