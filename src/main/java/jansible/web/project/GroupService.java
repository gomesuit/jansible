package jansible.web.project;

import jansible.mapper.GlobalRoleRelationMapper;
import jansible.mapper.RoleMapper;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.ProjectKey;
import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbGlobalRoleRelation;
import jansible.model.database.DbRole;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbServerRelation;
import jansible.model.database.DbServiceGroup;
import jansible.util.DbCommonUtils;
import jansible.web.project.group.RoleRelationForm;
import jansible.web.project.group.RoleRelationOrderForm;
import jansible.web.project.group.RoleRelationOrderType;
import jansible.web.project.group.ServerRelationForm;
import jansible.web.project.group.ServiceGroupForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class GroupService {
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private GlobalRoleRelationMapper globalRoleRelationMapper;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public void registServiceGroup(ServiceGroupForm form) {
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form);
		serviceGroupMapper.insertServiceGroup(dbServiceGroup);
	}

	public void deleteServiceGroup(ServiceGroupKey serviceGroupKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			transactionManager.commit(status);
			serviceGroupMapper.deleteServiceGroup(serviceGroupKey);
			serviceGroupMapper.deleteDbRoleRelationByServiceGroup(serviceGroupKey);
			serviceGroupMapper.deleteDbServerRelationByServiceGroup(serviceGroupKey);
			variableMapper.deleteDbServiceGroupVariableByServiceGroup(serviceGroupKey);
			fileService.deleteGroupVariableYaml(serviceGroupKey);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
	}

	public List<DbServiceGroup> getServiceGroupList(EnvironmentKey environmentKey){
		return serviceGroupMapper.selectServiceGroupList(environmentKey);
	}

	public void registRoleRelationDetail(RoleRelationForm form) {
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			DbRoleRelation dbRoleRelation = createDbRoleRelation(form);
			dbRoleRelation.setSort(serviceGroupMapper.selectDbRoleRelationList(form).size() + 1);
			serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
			
			fileService.outputRoleRelationData(form);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	public void deleteRoleRelation(RoleRelationKey roleRelationKey){
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			serviceGroupMapper.deleteDbRoleRelation(roleRelationKey);
			
			List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(roleRelationKey);
			DbCommonUtils.sortRoleRelation(dbRoleRelationList);
			modifyRoleRelationList(dbRoleRelationList);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	public List<DbRoleRelation> getRoleRelationList(ServiceGroupKey serviceGroupKey){
		return serviceGroupMapper.selectDbRoleRelationList(serviceGroupKey);
	}

	public void modifyRoleRelationOrder(RoleRelationOrderForm form) {
    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    	TransactionStatus status = transactionManager.getTransaction(def);

		try {
			List<DbRoleRelation> dbRoleRelationList = getOrderRoleRelationList(form);
			
			if(dbRoleRelationList != null){
				modifyRoleRelationList(dbRoleRelationList);
				fileService.outputRoleRelationData(form);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}
	
	private List<DbRoleRelation> getOrderRoleRelationList(RoleRelationOrderForm form){
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(form);
		int targetIndex = dbRoleRelationList.indexOf(new DbRoleRelation(form));
		
		if(form.getOrderType() == RoleRelationOrderType.UP){
			if(targetIndex - 1 < 0){
				return null;
			}
			Collections.swap(dbRoleRelationList, targetIndex, targetIndex - 1);
		}else if(form.getOrderType() == RoleRelationOrderType.DOWN){
			if(targetIndex + 1 >= dbRoleRelationList.size()){
				return null;
			}
			Collections.swap(dbRoleRelationList, targetIndex, targetIndex + 1);
		}else{
			return null;
		}
		
		DbCommonUtils.sortRoleRelation(dbRoleRelationList);
		
		return dbRoleRelationList;
	}

	private DbRoleRelation createDbRoleRelation(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = new DbRoleRelation(form);
		dbRoleRelation.setSort(form.getSort());
		return dbRoleRelation;
	}
	
	private void modifyRoleRelationList(List<DbRoleRelation> dbRoleRelationList){
		for(DbRoleRelation dbRoleRelation : dbRoleRelationList){
			serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
		}
	}

	public void registServerRelationDetail(ServerRelationForm form) {
		DbServerRelation dbServerRelation = new DbServerRelation(form);;
		serviceGroupMapper.insertDbServerRelation(dbServerRelation);
		
		fileService.outputHostsData(form);
		fileService.outputServerRelationData(form);
	}

	public void deleteServerRelation(ServerRelationKey key){
		serviceGroupMapper.deleteDbServerRelation(key);
	}

	public List<DbServerRelation> getServerRelationList(ServiceGroupKey key){
		return serviceGroupMapper.selectDbServerRelationList(key);
	}

	public List<DbServerRelation> getAllDbServerRelationList(ProjectKey key){
		return serviceGroupMapper.selectAllDbServerRelationList(key);
	}

	public List<String> getRoleNameList(ProjectKey key) {
		List<String> roleNameList = new ArrayList<>();
		List<DbRole> dbRoleList = roleMapper.selectRoleList(key);
		for(DbRole dbRole : dbRoleList){
			roleNameList.add(dbRole.getRoleName());
		}
		List<DbGlobalRoleRelation> dbGlobalRoleRelationList = globalRoleRelationMapper.selectRoleRelationList(key);
		for(DbGlobalRoleRelation dbGlobalRoleRelation : dbGlobalRoleRelationList){
			roleNameList.add(dbGlobalRoleRelation.getRoleName());
		}
		return roleNameList;
	}
}
