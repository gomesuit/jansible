package jansible.model;

public class Parameter {
	private String key;
	private String value;
	
	public Parameter(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return key + "=" + value;
	}
}
