package jansible;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	public static void main(String[] args) {
		
	    DumperOptions options = new DumperOptions();
	    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    options.setExplicitStart(true);
	    Yaml yaml = new Yaml(options);
	    String output = yaml.dump(createTasks2());
	    
		System.out.println(output);
	}
	
	public static List<Object> createTasks(){
		List<Object> list = new LinkedList<Object>();
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	    data.put("name", "install mysql");
	    data.put("yum", "name=mysql state=installed");

	    list.add(data);
	    
	    data = new LinkedHashMap<String, Object>();
	    data.put("name", "install mysql");
	    data.put("yum", "name=mysql state=installed");
	    //data.put("yum", new Yum());
	    list.add(data);
		
	    return list;
	}
	
	public static List<Object> createTasks2(){
		List<Object> list = new LinkedList<Object>();
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	    data.put("hosts", "all");
	    data.put("user", "root");
	    data.put("tasks", createTasks());
	    
	    list.add(data);
		
	    return list;
	}
	
	public static class Yum {
		String name = "mysql";
		String state = "installed";
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	}

}
