package jansible.model.common;

public class ApplyHistoryKey extends ProjectKey{
	private int applyHistroyId;
	
	public ApplyHistoryKey(){}

	public ApplyHistoryKey(ProjectKey projectKey){
		super(projectKey.getProjectName());
	}
	
	public ApplyHistoryKey(ProjectKey projectKey, int applyHistroyId) {
		this(projectKey);
		this.applyHistroyId = applyHistroyId;
	}

	public int getApplyHistroyId() {
		return applyHistroyId;
	}

	public void setApplyHistroyId(int applyHistroyId) {
		this.applyHistroyId = applyHistroyId;
	}
}
