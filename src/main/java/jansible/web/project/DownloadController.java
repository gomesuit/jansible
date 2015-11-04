package jansible.web.project;

import javax.servlet.http.HttpServletResponse;

import jansible.file.JansibleFiler;
import jansible.model.common.FileKey;
import jansible.model.common.TemplateKey;
import jansible.web.project.form.DownloadForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DownloadController {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	@RequestMapping(value="/project/download/template", method=RequestMethod.POST, produces = "application/force-download")
	@ResponseBody
    private Resource downloadTemplate(@ModelAttribute DownloadForm form, HttpServletResponse response){
		TemplateKey templateKey = new TemplateKey(form.getProjectName(), form.getRoleName(), form.getFileName());
		String templatePath = jansibleFiler.getTemplatePath(templateKey);
		response.setHeader("Content-Disposition","attachment; filename=\"" + form.getFileName() +"\"");
    	
		return new FileSystemResource(templatePath);
    }
	
	@RequestMapping(value="/project/download/file", method=RequestMethod.POST, produces = "application/force-download")
	@ResponseBody
    private Resource downloadFile(@ModelAttribute DownloadForm form, HttpServletResponse response){
		FileKey fileKey = new FileKey(form.getProjectName(), form.getRoleName(), form.getFileName());
		String filePath = jansibleFiler.getFilePath(fileKey);
		response.setHeader("Content-Disposition","attachment; filename=\"" + form.getFileName() +"\"");
    	
		return new FileSystemResource(filePath);
    }
}
