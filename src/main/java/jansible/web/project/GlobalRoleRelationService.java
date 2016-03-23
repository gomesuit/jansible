package jansible.web.project;

import jansible.mapper.GlobalRoleRelationMapper;
import jansible.model.common.GlobalRoleRelationKey;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbGlobalRole;
import jansible.model.database.DbGlobalRoleRelation;
import jansible.web.project.project.form.GlobalRoleRelationForm;
import jansible.web.project.project.form.GlobalRoleRelationTagUpdateForm;
import jansible.web.project.project.form.GlobalRoleRelationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class GlobalRoleRelationService {
	@Autowired
	private GlobalRoleRelationMapper globalRoleRelationMapper;

	@Autowired
	private GitService gitService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public List<DbGlobalRoleRelation> getGlobalRoleRelationList(ProjectKey key){
		return globalRoleRelationMapper.selectRoleRelationList(key);
	}
	
	public DbGlobalRoleRelation getGlobalRoleRelation(GlobalRoleRelationKey key){
		return globalRoleRelationMapper.selectRoleRelation(key);
	}

	public void registGlobalRoleRelation(GlobalRoleRelationForm form) throws Exception {
		DbGlobalRoleRelation dbGlobalRoleRelation = new DbGlobalRoleRelation(form);
		
		String tagName = form.getTagName();
		if(StringUtils.isBlank(tagName)){
			tagName = globalRoleRelationMapper.selectNewestTagName(form.getRoleName());
		}
		dbGlobalRoleRelation.setTagName(tagName);
		
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			globalRoleRelationMapper.insertRoleRelation(dbGlobalRoleRelation);
			gitService.addSubmodule(form, globalRoleRelationMapper.selectUri(form.getRoleName()), "roles/" + form.getRoleName());
			gitService.checkoutSubmodule(form, "roles/" + form.getRoleName(), tagName);
			gitService.initAndUpdateSubmodule(form);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	public void updateGlobalRoleRelation(GlobalRoleRelationTagUpdateForm form) throws Exception{
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		DbGlobalRoleRelation dbGlobalRoleRelation = new DbGlobalRoleRelation(form);
		dbGlobalRoleRelation.setTagName(form.getTagName());
		
		try {
			globalRoleRelationMapper.insertRoleRelation(dbGlobalRoleRelation);
			gitService.checkoutSubmodule(form, "roles/" + form.getRoleName(), form.getTagName());
			gitService.initAndUpdateSubmodule(form);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
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
