package jansible.model.common;

public class EnvironmentKey extends ProjectKey{
	private String environmentName;
	
	public EnvironmentKey(){}
	
	public EnvironmentKey(ProjectKey projectKey, String environmentName){
		super(projectKey.getProjectName());
		this.environmentName = environmentName;
	}
	
	public EnvironmentKey(String projectName, String environmentName){
		super(projectName);
		this.environmentName = environmentName;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
}
