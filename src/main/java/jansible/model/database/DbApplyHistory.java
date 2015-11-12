package jansible.model.database;

import java.util.Date;

import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.ServiceGroupKey;

public class DbApplyHistory extends ApplyHistoryKey{
	private String environmentName;
	private String groupName;
	private String serverName;
	private String tagName;
	private Date applyTime;
	
	public DbApplyHistory(){}

	public DbApplyHistory(ApplyHistoryKey applyHistoryKey) {
		super(applyHistoryKey, applyHistoryKey.getApplyHistroyId());
	}
	
	public DbApplyHistory(ServiceGroupKey serviceGroupKey) {
		super(serviceGroupKey);
		this.environmentName = serviceGroupKey.getEnvironmentName();
		this.groupName = serviceGroupKey.getGroupName();
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
