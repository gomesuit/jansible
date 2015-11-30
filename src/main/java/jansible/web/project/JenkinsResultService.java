package jansible.web.project;

import jansible.jenkins.JenkinsInfo;
import jansible.jenkins.result.JenkinsReader;
import jansible.mapper.ApplyHistoryMapper;
import jansible.mapper.ProjectMapper;
import jansible.model.common.ProjectKey;
import jansible.model.database.DbProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JenkinsResultService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ApplyHistoryMapper applyHistoryMapper;

	@Autowired
	private JenkinsReader jenkinsReader;
	
	public String getBuildResult(ProjectKey key, int applyHistroyId){
		
		DbProject dbProject = projectMapper.selectProject(key);
		
		JenkinsInfo info = new JenkinsInfo();
		info.setIpAddress(dbProject.getJenkinsIpAddress());
		info.setPort(dbProject.getJenkinsPort());
		info.setJobName(dbProject.getJenkinsJobName());
		
		return jenkinsReader.getBuildResult(info, applyHistroyId);
	}
}
