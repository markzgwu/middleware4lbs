package org.zgwu4lab.lbs.datamodel.geodata.node;

import java.io.Serializable;

public abstract class AbsPOIentry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String uid;
	LocationCoordinate location;
	String data;
	public final String getUid() {
		return uid;
	}
	public final void setUid(String uid) {
		this.uid = uid;
	}
	public final LocationCoordinate getLocation() {
		return location;
	}
	public final void setLocation(LocationCoordinate location) {
		this.location = location;
	}
	public final String getData() {
		return data;
	}
	public final void setData(String data) {
		this.data = data;
	}
	
}
