package sample;

import jansible.model.Yum;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class Main {

	public static void main(String[] args) {
	    List<Map<String, String>> data = new LinkedList<>();
	    data.add(createMap());
	    data.add(createMap());
		DumperOptions options = new DumperOptions();
	    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    Yaml yaml = new Yaml(options);
	    String output = yaml.dump(data);
	    Yum yum = new Yum();
	    yum.setName("mysql");
	    yum.setState("installed");
	    //output = yaml.dump(yum);
	    System.out.println(output);
	}
	
	public static Map<String, String> createMap(){
		Map<String, String> map = new HashMap<>();
		map.put("name", "mysql");
		map.put("aaa", "aaaa");
		return map;
	}
}
