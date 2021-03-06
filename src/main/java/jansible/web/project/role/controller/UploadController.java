package jansible.web.project.role.controller;

import jansible.web.project.RoleService;
import jansible.web.project.UploadService;
import jansible.web.project.role.form.GeneralFileForm;
import jansible.web.project.role.form.UploadForm;

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
	private RoleService roleService;
	
	@RequestMapping(value="/project/template/upload", method=RequestMethod.POST)
    private String uploadTemplate(@ModelAttribute UploadForm form, HttpServletRequest request){
		uploadService.templateUpload(form);
		roleService.registTemplate(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
	
	@RequestMapping(value="/project/file/upload", method=RequestMethod.POST)
    private String uploadFile(@ModelAttribute UploadForm form, HttpServletRequest request){
		uploadService.fileUpload(form);
		roleService.registFile(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
	
	@RequestMapping(value="/project/template/delete", method=RequestMethod.POST)
    private String deleteTemplate(@ModelAttribute GeneralFileForm form, HttpServletRequest request){
		uploadService.deleteTemplate(form);
		roleService.deleteTemplate(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
	
	@RequestMapping(value="/project/file/delete", method=RequestMethod.POST)
    private String deleteFile(@ModelAttribute GeneralFileForm form, HttpServletRequest request){
		uploadService.deleteFile(form);
		roleService.deleteFile(form);
    	
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
    }
}
