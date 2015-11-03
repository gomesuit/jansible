package jansible.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import jansible.model.yamldump.YamlModule;

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
		return data;
	}
}
