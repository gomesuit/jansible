package jansible.model.yamldump;

public class YamlParameter {
	private String key;
	private String value;
	
	public YamlParameter(String key, String value){
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
		if(key.equals("free_form")){
			return value;
		}else{
			return key + "=" + value;
		}
	}
}
