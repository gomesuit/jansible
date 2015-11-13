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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((environmentName == null) ? 0 : environmentName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvironmentKey other = (EnvironmentKey) obj;
		if (environmentName == null) {
			if (other.environmentName != null)
				return false;
		} else if (!environmentName.equals(other.environmentName))
			return false;
		return true;
	}
}
