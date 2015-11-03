package jansible.web.project;

import jansible.web.project.form.TemplateUploadForm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UploadController {
	@Autowired
	private UploadService uploadService;

	@RequestMapping(value="/project/template/upload", method=RequestMethod.POST)
    private String registTask(@ModelAttribute TemplateUploadForm form, HttpServletRequest request){
		uploadService.templateUpload(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
