package org.assistants.userdb;

public final class UserdbEntry{
	UserEntry userentry;
	String uid;
	String json;
	public final UserEntry getUserentry() {
		return userentry;
	}
	public final void setUserentry(UserEntry userentry) {
		this.userentry = userentry;
	}
	public final String getUid() {
		return uid;
	}
	public final void setUid(String uid) {
		this.uid = uid;
	}
	public final String getJson() {
		return json;
	}
	public final void setJson(String json) {
		this.json = json;
	}
	
}
