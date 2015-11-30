package jansible.jenkins.result;

import jansible.jenkins.JenkinsInfo;

public class JenkinsReadInfo extends JenkinsInfo {
	private int buildNumber;

	public int getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
}
