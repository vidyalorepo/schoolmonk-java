package com.dcc.schoolmonk.vo;

public class UserFeVo {

	private String fullName;
	private long userId;
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public long getUserKey() {
		return userId;
	}

	public void setUserKey(long userKey) {
		this.userId = userKey;
	}	
	

	@Override
	public String toString() {
		return "UserFeVo [fullName=" + fullName + ", userKey=" + userId + "]";
	}

}
