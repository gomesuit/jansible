package jansible.model.common;

public class EnvironmentKey extends ProjectKey{
	private String environmentName;
	
	public EnvironmentKey(){}

	public EnvironmentKey(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}
	
	public EnvironmentKey(ProjectKey projectKey, String environmentName){
		this(projectKey);
		this.environmentName = environmentName;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
}
