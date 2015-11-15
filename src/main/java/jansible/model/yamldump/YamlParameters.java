package jansible.model.yamldump;

import java.util.ArrayList;
import java.util.List;

public class YamlParameters {
	private List<YamlParameter> parameterList = new ArrayList<>();

	public void addParameter(YamlParameter param){
		parameterList.add(param);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		for(YamlParameter param : parameterList){
			result.append(param);
			result.append(" ");
		}
		
		return result.toString().trim();
	}
}
