package org.zgwu4lab.lbs.datamodel.json;

public final class JsonPOI {
	String uid;
	JsonLocation location;
	String address;
	String name;
	String telephone;
	public final String getUid() {
		return uid;
	}
	public final void setUid(String uid) {
		this.uid = uid;
	}
	public final JsonLocation getLocation() {
		return location;
	}
	public final void setLocation(JsonLocation location) {
		this.location = location;
	}
	public final String getAddress() {
		return address;
	}
	public final void setAddress(String address) {
		this.address = address;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getTelephone() {
		return telephone;
	}
	public final void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
