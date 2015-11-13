package jansible.model.yamldump;

import java.util.ArrayList;
import java.util.List;

public class YamlModule {
	private String name;
	private String description;
	private YamlParameters parameters;
	private List<Conditional> conditionalList = new ArrayList<>();
	
	public YamlModule(String name, YamlParameters parameters){
		this.name = name;
		this.parameters = parameters;
	}
	
	public void addConditional(String name, String value){
		Conditional conditional = new Conditional(name, value);
		conditionalList.add(conditional);
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
	public List<Conditional> getConditionalList() {
		return conditionalList;
	}
}
