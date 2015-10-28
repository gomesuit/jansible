package jansible.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import jansible.model.YamlModule;

public class YamlDumper {
	private DumperOptions options;
	private Yaml yaml;
    
    public YamlDumper(){
    	options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    yaml = new Yaml(options);
    }
	
	public String dump(YamlModule module){
		Map<String, String> data = new LinkedHashMap<>();
		data.put(module.getName(), module.getParameters().toString());
	    String result = yaml.dump(data);
	    return result;
	}
}
