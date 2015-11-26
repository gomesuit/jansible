package jansible.model.common;

public class GlobalRoleTagKey extends GlobalRoleKey{
	private String tagName;
	
	public GlobalRoleTagKey(){}

	public GlobalRoleTagKey(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public GlobalRoleTagKey(GlobalRoleKey key, String tagName) {
		this(key);
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
