package jansible.model.yamldump;

public class YamlModule {
	private String name;
	private YamlParameters parameters;
	
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
}
