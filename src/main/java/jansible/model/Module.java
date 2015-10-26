package jansible.model;

public class Module {
	private String name;
	private Parameters parameters;
	
	public Module(String name, Parameters parameters){
		this.name = name;
		this.parameters = parameters;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
}
