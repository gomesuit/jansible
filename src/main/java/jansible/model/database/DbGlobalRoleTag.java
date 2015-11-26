package jansible.model.database;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTagKey;

public class DbGlobalRoleTag extends GlobalRoleTagKey{
	private String tagComment;
	
	public DbGlobalRoleTag(){}

	public DbGlobalRoleTag(GlobalRoleKey key){
		super(key);
	}

	public DbGlobalRoleTag(GlobalRoleTagKey key) {
		super(key, key.getTagName());
	}

	public String getTagComment() {
		return tagComment;
	}

	public void setTagComment(String tagComment) {
		this.tagComment = tagComment;
	}
}
