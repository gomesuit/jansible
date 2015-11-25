package jansible.web.manager;

import java.util.Date;

import jansible.git.JansibleGitter;
import jansible.util.DateFormatter;
import jansible.web.manager.role.GitForm;
import jansible.web.manager.top.GlobalRoleForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerGitService {
	@Autowired
	private GlobalRoleService roleService;
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
	
	public void tagAndPush(GitForm form) throws Exception{
		String tagName = getTagName();
		roleService.registRoleTag(form, tagName);
		jansibleGitter.tagAndPush(form, form, tagName);
	}
	
	private String getTagName(){
		String dateString = DateFormatter.getDateString(new Date());
		return "VER" + dateString;
	}

}
