package jansible.jenkins;

public class JenkinsReadInfo extends JenkinsInfo {
	private int buildNumber;

	public int getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
}
