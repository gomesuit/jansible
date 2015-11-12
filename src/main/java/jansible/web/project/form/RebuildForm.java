package jansible.web.project.form;

import jansible.model.common.ApplyHistoryKey;
import jansible.model.common.ProjectKey;

public class RebuildForm extends ApplyHistoryKey{
	
	public RebuildForm(){}

	public RebuildForm(ProjectKey projectKey){
		super(projectKey);
	}
	
	public RebuildForm(ApplyHistoryKey applyHistoryKey){
		super(applyHistoryKey, applyHistoryKey.getApplyHistroyId());
	}
}
