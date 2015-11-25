package jansible.model.common;

public class GlobalRoleTagKey extends GlobalRoleKey{
	private int tagId;
	
	public GlobalRoleTagKey(){}

	public GlobalRoleTagKey(GlobalRoleKey key){
		super(key.getRoleName());
	}

	public GlobalRoleTagKey(GlobalRoleKey key, int tagId) {
		this(key);
		this.tagId = tagId;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	
}
