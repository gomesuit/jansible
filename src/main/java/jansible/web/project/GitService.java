package jansible.web.project;

import jansible.git.JansibleGitter;
import jansible.model.common.ProjectKey;
import jansible.web.project.project.GitConpareForm;
import jansible.web.project.project.GitForm;
import jansible.web.project.top.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitService {
	@Autowired
	private FileService fileService;
	@Autowired
	private JansibleGitter jansibleGitter;

	public void commitGit(GitForm form) throws Exception {
		fileService.reOutputAllData(form);
		jansibleGitter.commitAndPush(form, form.getUserName(), form.getPassword(), form.getComment());
	}
	
	public void cloneRepository(ProjectForm form) throws Exception {
		jansibleGitter.cloneRepository(form, form.getRepositoryUrl(), form);
	}
	
	public void addSubmodule(ProjectKey projectKey, String uri, String path) throws Exception{
		jansibleGitter.addSubmodule(projectKey, uri, path);
	}
	
	public void checkoutSubmodule(ProjectKey projectKey, String path, String tagName) throws Exception{
		jansibleGitter.checkoutSubmodule(projectKey, path, tagName);
	}

	public String getConpareUrl(String gitRepositoryUrl, GitConpareForm form) {
		String compareUrl = gitRepositoryUrl.replace(".git", "");
		compareUrl = compareUrl + "/compare/" + form.getBaseTag() + "..." + form.getConpareTag();
		
		return compareUrl;
	}
}
