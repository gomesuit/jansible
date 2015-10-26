package jansible.model;

import java.util.ArrayList;
import java.util.List;

public class Parameters {
	private List<Parameter> pList = new ArrayList<>();

	public void addParameter(Parameter param){
		pList.add(param);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for(Parameter param : pList){
			result.append(param);
			result.append(" ");
		}
		
		return result.toString().trim();
	}
}
