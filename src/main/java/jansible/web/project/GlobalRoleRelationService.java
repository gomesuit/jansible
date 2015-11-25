package jansible.web.project;

import jansible.mapper.GlobalRoleRelationMapper;
import jansible.model.common.GlobalRoleRelationKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleRelation;
import jansible.web.project.project.GlobalRoleRelationForm;

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
			gitService.addSubmodule(form, globalRoleRelationMapper.selectUri(form.getRoleName()), "roles/" + form.getRoleName(), tagName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteGlobalRoleRelation(GlobalRoleRelationKey key){
		globalRoleRelationMapper.deleteRoleRelation(key);
	}

	public List<DbGlobalRole> getGlobalRoleList(){
		return globalRoleRelationMapper.selectRoleList();
	}
}
