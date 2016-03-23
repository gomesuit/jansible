package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.git.JansibleGitter;
import jansible.model.common.ProjectKey;
import jansible.web.project.apply.form.GitForm;
import jansible.web.project.project.form.GitConpareForm;
import jansible.web.project.top.form.ProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitService {
	@Autowired
	private FileService fileService;
	@Autowired
	private JansibleGitter jansibleGitter;
	@Autowired
	private JansibleFiler jansibleFiler;

	public void commitGit(GitForm form) throws Exception {
		fileService.reOutputAllData(form);
		String projectDirName = jansibleFiler.getProjectDirName(form);
		jansibleGitter.commitAndPush(projectDirName, form.getUserName(), form.getPassword(), form.getComment());
	}
	
	public void cloneRepository(ProjectForm form) throws Exception {
		String projectDirName = jansibleFiler.getProjectDirName(form);
		jansibleGitter.callClone(form.getRepositoryUrl(), projectDirName, form);
	}
	
	public void addSubmodule(ProjectKey projectKey, String uri, String path) throws Exception{
		String dirName = jansibleFiler.getProjectDirName(projectKey);
		jansibleGitter.addSubmodule(dirName, uri, path);
	}
	
	public void checkoutSubmodule(ProjectKey projectKey, String path, String tagName) throws Exception{
		String dirName = jansibleFiler.getProjectDirName(projectKey);
		jansibleGitter.checkoutSubmodule(dirName, path, tagName);
	}
	
	public void initAndUpdateSubmodule(ProjectKey projectKey) throws Exception{
		String dirName = jansibleFiler.getProjectDirName(projectKey);
		jansibleGitter.initAndUpdateSubmodule(dirName);
	}

	public String getConpareUrl(String gitRepositoryUrl, GitConpareForm form) {
		String compareUrl = gitRepositoryUrl.replace(".git", "");
		compareUrl = compareUrl + "/compare/" + form.getBaseTag() + "..." + form.getConpareTag();
		
		return compareUrl;
	}
}
