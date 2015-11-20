package jansible.web.project;

import java.util.Date;

import jansible.file.JansibleFiler;
import jansible.git.JansibleGitter;
import jansible.jenkins.JenkinsBuilder;
import jansible.jenkins.JenkinsInfo;
import jansible.jenkins.JenkinsParameter;
import jansible.mapper.ApplyHistoryMapper;
import jansible.mapper.ProjectMapper;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbApplyHistory;
import jansible.model.database.DbProject;
import jansible.util.DateFormatter;
import jansible.web.project.apply.BuildForm;
import jansible.web.project.apply.ServerBuildForm;
import jansible.web.project.project.RebuildForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JenkinsBuildService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ApplyHistoryMapper applyHistoryMapper;
	
	@Autowired
	private JansibleFiler jansibleFiler;
	@Autowired
	private JansibleGitter jansibleGitter;
	@Autowired
	private JenkinsBuilder jenkinsBuilder;
	
	private JenkinsInfo createJenkinsInfo(ProjectKey projectKey){
		DbProject dbProject = projectMapper.selectProject(projectKey);
		return createJenkinsInfo(dbProject);
	}
	
	private JenkinsInfo createJenkinsInfo(DbProject dbProject){
		JenkinsInfo jenkinsInfo = new JenkinsInfo();
		jenkinsInfo.setIpAddress(dbProject.getJenkinsIpAddress());
		jenkinsInfo.setPort(dbProject.getJenkinsPort());
		jenkinsInfo.setJobName(dbProject.getJenkinsJobName());
		return jenkinsInfo;
	}
	
	public void rebuild(RebuildForm form) throws Exception{
		JenkinsInfo jenkinsInfo = createJenkinsInfo(form);
		
		DbApplyHistory dbApplyHistory = applyHistoryMapper.selectDbApplyHistory(form);
	
		DbProject project = projectMapper.selectProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getGroupName(dbApplyHistory.getEnvironmentName(), dbApplyHistory.getGroupName()));
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsParameter.setTagName(dbApplyHistory.getTagName());
		
		jenkinsBuilder.build(jenkinsInfo, jenkinsParameter);
		
		dbApplyHistory.setApplyTime(new Date());
		dbApplyHistory.setTagComment(dbApplyHistory.getTagComment() + "(rebuild)");
		applyHistoryMapper.insertDbApplyHistory(dbApplyHistory);
	}

	public void build(BuildForm form) throws Exception{
		String tagName = getTagName(form);
		jansibleGitter.tagAndPush(form, form, tagName);
		
		DbProject project = projectMapper.selectProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getGroupName(form));
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsParameter.setTagName(tagName);

		JenkinsInfo jenkinsInfo = createJenkinsInfo(form);
		
		jenkinsBuilder.build(jenkinsInfo, jenkinsParameter);
		
		DbApplyHistory dbApplyHistory = new DbApplyHistory();
		dbApplyHistory.setProjectName(form.getProjectName());
		dbApplyHistory.setEnvironmentName(form.getEnvironmentName());
		dbApplyHistory.setGroupName(form.getGroupName());
		dbApplyHistory.setTagName(tagName);
		dbApplyHistory.setTagComment(form.getComment());
		dbApplyHistory.setApplyTime(new Date());
		
		applyHistoryMapper.insertDbApplyHistory(dbApplyHistory);
	}
	
	private String getTagName(ServiceGroupKey serviceGroupKey){
		String groupName = jansibleFiler.getGroupName(serviceGroupKey);
		String dateString = DateFormatter.getDateString(new Date());
		return groupName + dateString;
	}

	public void buildforServer(ServerBuildForm form) throws Exception{
		String tagName = getTagNameForServer(form);
		jansibleGitter.tagAndPush(form, form, tagName);
		
		DbProject project = projectMapper.selectProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getServerStartYamlName(form));
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsParameter.setTagName(tagName);

		JenkinsInfo jenkinsInfo = createJenkinsInfo(form);
		
		jenkinsBuilder.build(jenkinsInfo, jenkinsParameter);
		
		DbApplyHistory dbApplyHistory = new DbApplyHistory();
		dbApplyHistory.setProjectName(form.getProjectName());
		dbApplyHistory.setEnvironmentName(form.getEnvironmentName());
		dbApplyHistory.setGroupName(form.getGroupName());
		dbApplyHistory.setServerName(form.getServerName());
		dbApplyHistory.setTagName(tagName);
		dbApplyHistory.setTagComment(form.getComment());
		dbApplyHistory.setApplyTime(new Date());
		
		applyHistoryMapper.insertDbApplyHistory(dbApplyHistory);
	}
	
	private String getTagNameForServer(ServerRelationKey key){
		String groupName = jansibleFiler.getServerStartYamlName(key);
		String dateString = DateFormatter.getDateString(new Date());
		return groupName + dateString;
	}
}
