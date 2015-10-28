package jansible;

import java.io.IOException;
import java.util.List;

import jansible.model2.Module;
import jansible.model2.Parameter;
import jansible.webget.ModuleGetter;
import jansible.webget.ModuleUrlGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class SampleController {
	
	@Autowired
	private JansibleService jansibleService;

    @RequestMapping("/save")
    @ResponseBody
    String save() throws IOException, InterruptedException {
    	
    	List<String> UrlList = ModuleUrlGetter.getUrlList();
    	
    	for(String url : UrlList){
    		System.out.println(url);
    		Module module = ModuleGetter.getModule(url);
    		System.out.println(module);
    		for(Parameter parameter : module.getParameterList()){
        		System.out.println(parameter);
    		}
    		jansibleService.insertModule(module);
    		
    		Thread.sleep(3000);
    	}
    	
        return "Hello World!";
    }

    @RequestMapping("/module/{moduleName}")
    @ResponseBody
    String module(@PathVariable String moduleName) {
    	Module module = jansibleService.getModule(moduleName);
    	
        return module.toString();
    }
}
