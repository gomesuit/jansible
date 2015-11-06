package jansible.web.project;

import jansible.web.project.form.GeneralFileForm;
import jansible.web.project.form.UploadForm;

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
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/project/template/upload", method=RequestMethod.POST)
    private String uploadTemplate(@ModelAttribute UploadForm form, HttpServletRequest request){
		uploadService.templateUpload(form);
		projectService.registTemplate(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
	
	@RequestMapping(value="/project/file/upload", method=RequestMethod.POST)
    private String uploadFile(@ModelAttribute UploadForm form, HttpServletRequest request){
		uploadService.fileUpload(form);
		projectService.registFile(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
	
	@RequestMapping(value="/project/template/delete", method=RequestMethod.POST)
    private String deleteTemplate(@ModelAttribute GeneralFileForm form, HttpServletRequest request){
		uploadService.deleteTemplate(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
	
	@RequestMapping(value="/project/file/delete", method=RequestMethod.POST)
    private String deleteFile(@ModelAttribute GeneralFileForm form, HttpServletRequest request){
		uploadService.deleteFile(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
