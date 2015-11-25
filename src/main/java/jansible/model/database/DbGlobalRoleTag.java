package jansible.model.database;

import jansible.model.common.GlobalRoleKey;
import jansible.model.common.GlobalRoleTagKey;

public class DbGlobalRoleTag extends GlobalRoleTagKey{
	private String tagName;
	private String tagComment;
	
	public DbGlobalRoleTag(){}

	public DbGlobalRoleTag(GlobalRoleKey key){
		super(key);
	}

	public DbGlobalRoleTag(GlobalRoleTagKey key) {
		super(key, key.getTagId());
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagComment() {
		return tagComment;
	}

	public void setTagComment(String tagComment) {
		this.tagComment = tagComment;
	}
}
