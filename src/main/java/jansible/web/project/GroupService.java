package jansible.web.project;

import jansible.file.JansibleFiler;
import jansible.mapper.ServerMapper;
import jansible.mapper.ServiceGroupMapper;
import jansible.mapper.VariableMapper;
import jansible.model.common.EnvironmentKey;
import jansible.model.common.RoleRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbRoleRelation;
import jansible.model.database.DbServiceGroup;
import jansible.web.project.group.RoleRelationForm;
import jansible.web.project.group.RoleRelationOrderForm;
import jansible.web.project.group.RoleRelationOrderType;
import jansible.web.project.group.ServiceGroupForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
	@Autowired
	private ServiceGroupMapper serviceGroupMapper;
	@Autowired
	private ServerMapper serverMapper;
	@Autowired
	private VariableMapper variableMapper;
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private FileService fileService;

	public void registServiceGroup(ServiceGroupForm form) {
		DbServiceGroup dbServiceGroup = new DbServiceGroup(form);
		serviceGroupMapper.insertServiceGroup(dbServiceGroup);
	}

	public void deleteServiceGroup(ServiceGroupKey serviceGroupKey){
		serviceGroupMapper.deleteServiceGroup(serviceGroupKey);
		serviceGroupMapper.deleteDbRoleRelationByServiceGroup(serviceGroupKey);
		serverMapper.deleteServerByServiceGroup(serviceGroupKey);
		variableMapper.deleteDbServiceGroupVariableByServiceGroup(serviceGroupKey);
		variableMapper.deleteDbServerVariableByServiceGroup(serviceGroupKey);
		jansibleFiler.deleteGroupVariableYaml(serviceGroupKey);
	}

	public List<DbServiceGroup> getServiceGroupList(EnvironmentKey environmentKey){
		return serviceGroupMapper.selectServiceGroupList(environmentKey);
	}

	public void deleteRoleRelation(RoleRelationKey roleRelationKey){
		serviceGroupMapper.deleteDbRoleRelation(roleRelationKey);
		
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(roleRelationKey);
		sortRoleRelation(dbRoleRelationList);
	}

	public List<DbRoleRelation> getRoleRelationList(ServiceGroupKey serviceGroupKey){
		return serviceGroupMapper.selectDbRoleRelationList(serviceGroupKey);
	}

	public void registRoleRelationDetail(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = createDbRoleRelation(form);
		dbRoleRelation.setSort(serviceGroupMapper.selectDbRoleRelationList(form).size() + 1);
		serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
		
		fileService.outputRoleRelationData(form);
	}

	private DbRoleRelation createDbRoleRelation(RoleRelationForm form) {
		DbRoleRelation dbRoleRelation = new DbRoleRelation(form);
		dbRoleRelation.setSort(form.getSort());
		return dbRoleRelation;
	}

	public void modifyRoleRelationOrder(RoleRelationOrderForm roleRelationOrderForm) {
		List<DbRoleRelation> dbRoleRelationList = serviceGroupMapper.selectDbRoleRelationList(roleRelationOrderForm);
		int tagetIndex = 0;
		DbRoleRelation targetDbRoleRelation = null;
		
		for(int i = 0; i < dbRoleRelationList.size(); i++){
			DbRoleRelation dbRoleRelation = dbRoleRelationList.get(i);
			RoleRelationKey comparisonRoleRelationKey = new RoleRelationKey(dbRoleRelation, dbRoleRelation.getRoleName());
			RoleRelationKey targetRoleRelationKey = new RoleRelationKey(roleRelationOrderForm, roleRelationOrderForm.getRoleName());
			if(comparisonRoleRelationKey.equals(targetRoleRelationKey)){
				tagetIndex = i;
				targetDbRoleRelation = dbRoleRelation;
				break;
			}
		}
		
		if(roleRelationOrderForm.getOrderType() == RoleRelationOrderType.UP){
			if(tagetIndex - 1 < 0){
				return;
			}

			dbRoleRelationList.remove(tagetIndex);
			dbRoleRelationList.add(tagetIndex - 1, targetDbRoleRelation);
		}else{
			if(tagetIndex + 1 >= dbRoleRelationList.size()){
				return;
			}
			
			dbRoleRelationList.remove(tagetIndex);
			dbRoleRelationList.add(tagetIndex + 1, targetDbRoleRelation);
		}
		
		sortRoleRelation(dbRoleRelationList);
		
		fileService.outputRoleRelationData(roleRelationOrderForm);
	}
	
	private void sortRoleRelation(List<DbRoleRelation> dbRoleRelationList){
		for(int i = 0; i < dbRoleRelationList.size(); i++){
			DbRoleRelation dbRoleRelation = dbRoleRelationList.get(i);
			dbRoleRelation.setSort(i + 1);
			serviceGroupMapper.insertDbRoleRelation(dbRoleRelation);
		}
	}
}
