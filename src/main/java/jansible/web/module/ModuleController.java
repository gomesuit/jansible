package jansible.web.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.model.yamldump.YamlModule;
import jansible.model.yamldump.YamlParameter;
import jansible.model.yamldump.YamlParameters;
import jansible.util.YamlDumper;
import jansible.web.module.form.FormParameter;
import jansible.web.module.form.ModuleForm;
import jansible.webget.ModuleGetter;
import jansible.webget.ModuleUrlGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ModuleController {
	
	@Autowired
	private ModuleService jansibleService;
	@Autowired
	private YamlDumper yamlDumper;

    @RequestMapping("/save")
    @ResponseBody
    String save() throws IOException, InterruptedException {
    	
    	List<String> UrlList = ModuleUrlGetter.getUrlList();
    	
    	for(String url : UrlList){
    		System.out.println(url);
    		HtmlModule module = ModuleGetter.getModule(url);
    		System.out.println(module);
    		for(HtmlParameter parameter : module.getParameterList()){
        		System.out.println(parameter);
    		}
    		jansibleService.insertModule(module);
    		
    		Thread.sleep(3000);
    	}
    	
        return "Hello World!";
    }

    @RequestMapping("/module/{moduleName}")
    String module(@PathVariable String moduleName, Model model) {
    	HtmlModule module = jansibleService.getModule(moduleName);
    	
    	model.addAttribute("module", module);
    	
        return "sample";
    }

    @RequestMapping("/module/{moduleName}/create")
    String yamlmodule(@PathVariable String moduleName, Model model) {
    	HtmlModule module = jansibleService.getModule(moduleName);
    	
    	List<FormParameter> formParameterList = new ArrayList<>();
    	for(HtmlParameter parameter : module.getParameterList()){
    		FormParameter formParameter = new FormParameter();
    		formParameter.setKey(parameter.getName());
    		formParameterList.add(formParameter);
    	}
    	ModuleForm form = new ModuleForm();
    	form.setModuleName(moduleName);
    	form.setParameterList(formParameterList);
    	model.addAttribute("form", form);
    	
        return "yamlcreate";
    }

    @RequestMapping("/module")
    String module(Model model) {
    	List<String> moduleNameList = jansibleService.getModuleNameList();
    	model.addAttribute("moduleNameList", moduleNameList);
    	
        return "moduleList";
    }
    
    @RequestMapping("/create")
    String create(Model model) {
    	model.addAttribute("form", new ModuleForm());
        return "create";
    }
    
    @RequestMapping(value="/create/view", method=RequestMethod.POST)
    @ResponseBody
    String view(@ModelAttribute ModuleForm form) {
		YamlModule module = createMolule(form);
        return yamlDumper.dump(module);
    }

	private YamlModule createMolule(ModuleForm form) {
		YamlModule yamlModule = new YamlModule(form.getModuleName(), createParameter(form));
		return yamlModule;
	}

	private YamlParameters createParameter(ModuleForm form) {
		YamlParameters parameters = new YamlParameters();
		for(FormParameter formParameter : form.getParameterList()){
			parameters.addParameter(new YamlParameter(formParameter.getKey(), formParameter.getValue()));
		}
		return parameters;
	}
}
