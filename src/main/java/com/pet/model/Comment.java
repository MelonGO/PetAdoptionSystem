package com.pet.model;

public class Comment extends Base{
	private int petID;
	private String username;
	private String content;
	private int fatherCommentID;
	private int replyCommentID;
	private int support;
	
	public int getPetID() {
		return petID;
	}
	public String getUsername() {
		return username;
	}
	public String getContent() {
		return content;
	}
	public int getFatherCommentID() {
		return fatherCommentID;
	}
	public int getSupport() {
		return support;
	}
	public void setPetID(int petID) {
		this.petID = petID;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setFatherCommentID(int fatherCommentID) {
		this.fatherCommentID = fatherCommentID;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	public int getReplyCommentID() {
		return replyCommentID;
	}
	public void setReplyCommentID(int replyCommentID) {
		this.replyCommentID = replyCommentID;
	}
	
	
}
