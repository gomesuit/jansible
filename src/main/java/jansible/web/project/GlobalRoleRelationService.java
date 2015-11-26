package jansible.web.project;

import jansible.mapper.GlobalRoleRelationMapper;
import jansible.model.common.GlobalRoleRelationKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleRelation;
import jansible.web.project.project.GlobalRoleRelationForm;
import jansible.web.project.project.GlobalRoleRelationTagUpdateForm;
import jansible.web.project.project.GlobalRoleRelationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalRoleRelationService {
	@Autowired
	private GlobalRoleRelationMapper globalRoleRelationMapper;

	@Autowired
	private GitService gitService;

	public List<DbGlobalRoleRelation> getGlobalRoleRelationList(ProjectKey key){
		return globalRoleRelationMapper.selectRoleRelationList(key);
	}
	
	public DbGlobalRoleRelation getGlobalRoleRelation(GlobalRoleRelationKey key){
		return globalRoleRelationMapper.selectRoleRelation(key);
	}

	public void registGlobalRoleRelation(GlobalRoleRelationForm form) {
		DbGlobalRoleRelation dbGlobalRoleRelation = new DbGlobalRoleRelation(form);
		
		String tagName = form.getTagName();
		if(StringUtils.isBlank(tagName)){
			tagName = globalRoleRelationMapper.selectNewestTagName(form.getRoleName());
		}
		dbGlobalRoleRelation.setTagName(tagName);
		
		globalRoleRelationMapper.insertRoleRelation(dbGlobalRoleRelation);
		
		try {
			gitService.addSubmodule(form, globalRoleRelationMapper.selectUri(form.getRoleName()), "roles/" + form.getRoleName());
			gitService.checkoutSubmodule(form, "roles/" + form.getRoleName(), tagName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteGlobalRoleRelation(GlobalRoleRelationTagUpdateForm form){
		try {
			DbGlobalRoleRelation dbGlobalRoleRelation = new DbGlobalRoleRelation(form);
			dbGlobalRoleRelation.setTagName(form.getTagName());
			globalRoleRelationMapper.insertRoleRelation(dbGlobalRoleRelation);
			gitService.checkoutSubmodule(form, "roles/" + form.getRoleName(), form.getTagName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<DbGlobalRole> getGlobalRoleList(){
		return globalRoleRelationMapper.selectRoleList();
	}

	public List<GlobalRoleRelationView> getGlobalRoleRelationViewList(ProjectKey projectKey) {
		List<GlobalRoleRelationView> viewList = new ArrayList<>();
		List<DbGlobalRoleRelation> DbList = getGlobalRoleRelationList(projectKey);
		for(DbGlobalRoleRelation Db : DbList){
			GlobalRoleRelationView view = createGlobalRoleRelationView(Db);
			viewList.add(view);
		}
		return viewList;
	}

	private GlobalRoleRelationView createGlobalRoleRelationView(DbGlobalRoleRelation db) {
		GlobalRoleRelationView view = new GlobalRoleRelationView(db);
		List<String> tagList = globalRoleRelationMapper.selectTagNameList(db.getRoleName());
		view.setTagList(tagList);
		return view;
	}
}
