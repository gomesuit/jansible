package jansible.model.yamldump;

public class YamlModule {
	private String name;
	private YamlParameters parameters;
	private String description;
	
	public YamlModule(String name, YamlParameters parameters){
		this.name = name;
		this.parameters = parameters;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public YamlParameters getParameters() {
		return parameters;
	}
	public void setParameters(YamlParameters parameters) {
		this.parameters = parameters;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
