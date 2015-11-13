package jansible.web.project.task;

public class TaskView {
	private Integer taskId;
	private String moduleName;
	private String description;
	private String parametersValue;
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParametersValue() {
		return parametersValue;
	}
	public void setParametersValue(String parametersValue) {
		this.parametersValue = parametersValue;
	}
}
