package jansible.web.project;

import jansible.git.JansibleGitter;
import jansible.web.project.project.GitForm;
import jansible.web.project.project.GlobalRoleRelationForm;
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
		jansibleGitter.cloneRepository(form, form.getRepositoryUrl());
	}
	
	public void addSubmodule(GlobalRoleRelationForm form, String uri, String path, String tagName) throws Exception{
		jansibleGitter.addSubmodule(form, uri, path, tagName);
	}
}
