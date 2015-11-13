package jansible.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import jansible.model.yamldump.Conditional;
import jansible.model.yamldump.StartYaml;
import jansible.model.yamldump.YamlModule;
import jansible.model.yamldump.YamlVariable;

@Service
public class YamlDumper {
	private DumperOptions options;
	private Yaml yaml;
    
    public YamlDumper(){
    	options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    options.setExplicitStart(true);
	    yaml = new Yaml(options);
    }
	
	public String dump(YamlModule module){
		List<YamlModule> modules = new ArrayList<>();
		modules.add(module);
	    return dump(modules);
	}
	
	public String dump(List<YamlModule> modules){
		List<Map<String, String>> dataList = new ArrayList<>();
		for(YamlModule module : modules){
			Map<String, String> data = createDataMap(module);
			dataList.add(data);
		}
	    return yaml.dump(dataList);
	}
	
	private Map<String, String> createDataMap(YamlModule module){
		Map<String, String> data = new LinkedHashMap<>();
		if(!StringUtils.isBlank(module.getDescription())){
			data.put("name", module.getDescription());
		}
		data.put(module.getName(), module.getParameters().toString());
		for(Conditional conditional : module.getConditionalList()){
			data.put(conditional.getName(), conditional.getValue());
		}
		return data;
	}
	
	private Map<String, String> createDataMap(List<YamlVariable> variableList){
		Map<String, String> data = new LinkedHashMap<>();
		for(YamlVariable variable : variableList){
			data.put(variable.getKey(), variable.getValue());
		}
		return data;
	}
	
	public String dumpVariable(List<YamlVariable> variableList){
		Map<String, String> data = createDataMap(variableList);
	    return yaml.dump(data);
	}
	
	public static void main(String args[]){
		StartYaml startYaml = new StartYaml();
		startYaml.setHosts("localhost");
		startYaml.addRole("common");
		startYaml.addRole("bundler");
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("hosts", startYaml.getHosts());
		data.put("sudo", "yes");
		data.put("roles", startYaml.getRoles());
		dataList.add(data);
		DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    options.setExplicitStart(true);
	    Yaml yaml = new Yaml(options);
		System.out.println(yaml.dump(dataList));
	}
	
	public String dumpStartYaml(StartYaml startYaml){
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("hosts", startYaml.getHosts());
		data.put("sudo", "yes");
		data.put("roles", startYaml.getRoles());
		dataList.add(data);
		return yaml.dump(dataList);
	}
}
