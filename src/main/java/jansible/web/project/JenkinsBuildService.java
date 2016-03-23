package jansible.web.project;

import java.util.Date;

import jansible.file.JansibleFiler;
import jansible.git.JansibleGitter;
import jansible.jenkins.JenkinsInfo;
import jansible.jenkins.JenkinsParameter;
import jansible.jenkins.build.JenkinsBuilder;
import jansible.mapper.ApplyHistoryMapper;
import jansible.mapper.ProjectMapper;
import jansible.model.common.ProjectKey;
import jansible.model.common.ServerKey;
import jansible.model.common.ServerRelationKey;
import jansible.model.common.ServiceGroupKey;
import jansible.model.database.DbApplyHistory;
import jansible.model.database.DbProject;
import jansible.util.DateFormatter;
import jansible.web.project.apply.form.BuildForm;
import jansible.web.project.apply.form.ServerBuildForm;
import jansible.web.project.project.form.RebuildForm;

import org.apache.commons.lang.StringUtils;
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
	
	public int rebuild(RebuildForm form) throws Exception{
		DbApplyHistory dbApplyHistory = applyHistoryMapper.selectDbApplyHistory(form);
		
		dbApplyHistory.setApplyTime(new Date());
		dbApplyHistory.setTagComment(dbApplyHistory.getTagComment() + "(rebuild)");
		applyHistoryMapper.insertDbApplyHistory(dbApplyHistory);
		
		DbProject project = projectMapper.selectProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getGroupName(dbApplyHistory.getEnvironmentName(), dbApplyHistory.getGroupName()));
		String serverName = dbApplyHistory.getServerName();
		if(StringUtils.isBlank(serverName)){
			jenkinsParameter.setHostsFileName(jansibleFiler.getHostsFileName());
		}else{
			ServerKey serverKey = new ServerKey(form);
			serverKey.setServerName(serverName);
			jenkinsParameter.setHostsFileName(jansibleFiler.getServerHostsFileName(serverKey));
		}
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsParameter.setTagName(dbApplyHistory.getTagName());
		jenkinsParameter.setApplyHistroyId(dbApplyHistory.getApplyHistroyId());
		JenkinsInfo jenkinsInfo = createJenkinsInfo(form);
		jenkinsBuilder.build(jenkinsInfo, jenkinsParameter);
		
		return dbApplyHistory.getApplyHistroyId();
	}

	public int groupBuild(BuildForm form) throws Exception{
		String tagName = getTagName(form);
		String projectDirName = jansibleFiler.getProjectDirName(form);
		jansibleGitter.tagAndPush(projectDirName, form, tagName);
		
		DbApplyHistory dbApplyHistory = new DbApplyHistory();
		dbApplyHistory.setProjectName(form.getProjectName());
		dbApplyHistory.setEnvironmentName(form.getEnvironmentName());
		dbApplyHistory.setGroupName(form.getGroupName());
		dbApplyHistory.setTagName(tagName);
		dbApplyHistory.setTagComment(form.getComment());
		dbApplyHistory.setApplyTime(new Date());
		applyHistoryMapper.insertDbApplyHistory(dbApplyHistory);
		
		DbProject project = projectMapper.selectProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getGroupName(form));
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsParameter.setTagName(tagName);
		jenkinsParameter.setApplyHistroyId(dbApplyHistory.getApplyHistroyId());
		jenkinsParameter.setHostsFileName(jansibleFiler.getHostsFileName());
		JenkinsInfo jenkinsInfo = createJenkinsInfo(form);
		jenkinsBuilder.build(jenkinsInfo, jenkinsParameter);
		
		return dbApplyHistory.getApplyHistroyId();
	}
	
	private String getTagName(ServiceGroupKey serviceGroupKey){
		String groupName = jansibleFiler.getGroupName(serviceGroupKey);
		String dateString = DateFormatter.getDateString(new Date());
		return groupName + "_" + dateString;
	}

	public int buildforServer(ServerBuildForm form) throws Exception{
		String tagName = getTagNameForServer(form);
		String projectDirName = jansibleFiler.getProjectDirName(form);
		jansibleGitter.tagAndPush(projectDirName, form, tagName);
		
		DbApplyHistory dbApplyHistory = new DbApplyHistory();
		dbApplyHistory.setProjectName(form.getProjectName());
		dbApplyHistory.setEnvironmentName(form.getEnvironmentName());
		dbApplyHistory.setGroupName(form.getGroupName());
		dbApplyHistory.setServerName(form.getServerName());
		dbApplyHistory.setTagName(tagName);
		dbApplyHistory.setTagComment(form.getComment());
		dbApplyHistory.setApplyTime(new Date());
		applyHistoryMapper.insertDbApplyHistory(dbApplyHistory);

		DbProject project = projectMapper.selectProject(form);
		JenkinsParameter jenkinsParameter = new JenkinsParameter();
		jenkinsParameter.setProjectName(form.getProjectName());
		jenkinsParameter.setGroupName(jansibleFiler.getGroupName(form));
		jenkinsParameter.setRepositoryUrl(project.getRepositoryUrl());
		jenkinsParameter.setTagName(tagName);
		jenkinsParameter.setApplyHistroyId(dbApplyHistory.getApplyHistroyId());
		ServerKey serverKey = new ServerKey(form);
		serverKey.setServerName(form.getServerName());
		jenkinsParameter.setHostsFileName(jansibleFiler.getServerHostsFileName(serverKey));

		JenkinsInfo jenkinsInfo = createJenkinsInfo(form);
		
		jenkinsBuilder.build(jenkinsInfo, jenkinsParameter);
		
		return dbApplyHistory.getApplyHistroyId();
	}
	
	private String getTagNameForServer(ServerRelationKey key){
		String groupName = jansibleFiler.getServerStartYamlName(key);
		String dateString = DateFormatter.getDateString(new Date());
		return groupName + "_" + dateString;
	}
}
