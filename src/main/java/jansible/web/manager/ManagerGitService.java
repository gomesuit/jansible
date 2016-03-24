package jansible.web.manager;

import java.util.Date;

import jansible.file.JansibleFiler;
import jansible.git.JansibleGitter;
import jansible.util.DateFormatter;
import jansible.web.manager.role.form.GitForm;
import jansible.web.manager.top.form.GlobalRoleForm;

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
	@Autowired
	private JansibleFiler jansibleFiler;

	public void commitGit(GitForm form) throws Exception {
		fileService.reOutputAllData(form);
		String dirName = jansibleFiler.getGlobalRoleDirName(form);
		jansibleGitter.commitAndPush(dirName, form.getUserName(), form.getPassword(), form.getComment());
	}
	
	public void cloneRoleRepository(GlobalRoleForm form) throws Exception {
		String dirName = jansibleFiler.getGlobalRoleDirName(form);
		jansibleGitter.callClone(form.getRepositoryUrl(), dirName, form);
	}
	
	public void tagAndPush(GitForm form) throws Exception{
		String tagName = getTagName();
		roleService.registRoleTag(form, tagName);
		String dirName = jansibleFiler.getGlobalRoleDirName(form);
		jansibleGitter.tagAndPush(dirName, form, tagName);
	}
	
	private String getTagName(){
		String dateString = DateFormatter.getDateString(new Date());
		return "VER" + dateString;
	}

}
