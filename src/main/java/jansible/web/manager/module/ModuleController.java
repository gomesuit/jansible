package jansible.web.manager.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jansible.model.gethtml.HtmlModule;
import jansible.model.gethtml.HtmlParameter;
import jansible.model.yamldump.YamlDumper;
import jansible.model.yamldump.YamlModule;
import jansible.model.yamldump.YamlParameter;
import jansible.model.yamldump.YamlParameters;
import jansible.webget.ModuleGetter;
import jansible.webget.ModuleUrlGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/manager/module/view")
    String module(@RequestParam(value = "moduleName", required = true) String moduleName,
    		Model model, HttpServletRequest request) {
    	HtmlModule module = jansibleService.getModule(moduleName);
    	
    	model.addAttribute("module", module);
    	
		request.setAttribute("pageName", "manager/module/sample");
		return "common_frame";
    }

    @RequestMapping("/manager/module/create")
    String yamlmodule(@RequestParam(value = "moduleName", required = true) String moduleName,
    		Model model, HttpServletRequest request) {
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
    	
		request.setAttribute("pageName", "manager/module/yamlcreate");
		return "common_frame";
    }

    @RequestMapping("/manager/module")
    String module(Model model, HttpServletRequest request) {
    	List<ModuleRow> moduleRowList = jansibleService.getModuleList();
    	
    	AvailableModuleForm availableModuleForm = new AvailableModuleForm();
    	availableModuleForm.setAvailableModuleRowList(moduleRowList);
    	model.addAttribute("availableModuleForm", availableModuleForm);
    	
		request.setAttribute("pageName", "manager/module/moduleList");
		return "common_frame";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(768);
    }

    @RequestMapping(value="/manager/module/registAvailableModule", method=RequestMethod.POST)
    String insertAvailableModuleList(@ModelAttribute AvailableModuleForm availableModuleForm, Model model, HttpServletRequest request) {
    	jansibleService.registAvailableModuleList(availableModuleForm.getAvailableModuleRowList());
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
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
