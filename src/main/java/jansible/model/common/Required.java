package jansible.model.common;

public enum Required {
	yes(true), no(false);
	
	private final boolean required;
	
	private Required(boolean required){
		this.required = required;
	}
	
	public boolean isRequired(){
		return this.required;
	}
	
	public static Required getRequired(boolean isRequired){
		if(isRequired){
			return Required.yes;
		}else{
			return Required.no;
		}
	}
}
