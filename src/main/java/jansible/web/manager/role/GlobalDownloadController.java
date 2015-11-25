package jansible.web.manager.role;

import javax.servlet.http.HttpServletResponse;

import jansible.file.JansibleFiler;
import jansible.model.common.GlobalRoleFileKey;
import jansible.model.common.GlobalRoleTemplateKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GlobalDownloadController {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	@RequestMapping(value="/manager/download/template", method=RequestMethod.POST, produces = "application/force-download")
	@ResponseBody
    private Resource downloadTemplate(@ModelAttribute GeneralFileForm form, HttpServletResponse response){
		GlobalRoleTemplateKey templateKey = new GlobalRoleTemplateKey(form);
		templateKey.setTemplateName(form.getFileName());
		String templatePath = jansibleFiler.getGlobalRoleTemplatePath(templateKey);
		response.setHeader("Content-Disposition","attachment; filename=\"" + form.getFileName() +"\"");
    	
		return new FileSystemResource(templatePath);
    }
	
	@RequestMapping(value="/manager/download/file", method=RequestMethod.POST, produces = "application/force-download")
	@ResponseBody
    private Resource downloadFile(@ModelAttribute GeneralFileForm form, HttpServletResponse response){
		GlobalRoleFileKey fileKey = new GlobalRoleFileKey(form);
		fileKey.setFileName(form.getFileName());
		String filePath = jansibleFiler.getGlobalRoleFilePath(fileKey);
		response.setHeader("Content-Disposition","attachment; filename=\"" + form.getFileName() +"\"");
    	
		return new FileSystemResource(filePath);
    }
}
