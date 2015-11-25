package jansible.web.manager;

import jansible.git.JansibleGitter;
import jansible.web.manager.role.GitForm;
import jansible.web.manager.top.GlobalRoleForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerGitService {
	@Autowired
	private ManagerFileService fileService;
	@Autowired
	private JansibleGitter jansibleGitter;

	public void commitGit(GitForm form) throws Exception {
		fileService.reOutputAllData(form);
		jansibleGitter.commitAndPush(form, form.getUserName(), form.getPassword(), form.getComment());
	}
	
	public void cloneRepository(GlobalRoleForm form) throws Exception {
		jansibleGitter.cloneRoleRepository(form, form.getRepositoryUrl());
	}
}
