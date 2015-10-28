package jansible.model;

import java.util.ArrayList;
import java.util.List;

public class YamlParameters {
	private List<YamlParameter> pList = new ArrayList<>();
	private String freeForm;

	public void addParameter(YamlParameter param){
		pList.add(param);
	}

	public void setFreeForm(String freeForm) {
		this.freeForm = freeForm;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		if(freeForm != null){
			result.append(freeForm);
			result.append(" ");
		}
		
		for(YamlParameter param : pList){
			result.append(param);
			result.append(" ");
		}
		
		return result.toString().trim();
	}
}
