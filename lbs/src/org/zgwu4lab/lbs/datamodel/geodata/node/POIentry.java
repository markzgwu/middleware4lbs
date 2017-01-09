package org.zgwu4lab.lbs.datamodel.geodata.node;

import com.alibaba.fastjson.JSON;

public final class POIentry extends AbsPOIentry{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String address;
	String info;
	String time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public final String getInfo() {
		return info;
	}
	public final void setInfo(String info) {
		this.info = info;
	}
	public final String getTime() {
		return time;
	}
	public final void setTime(String time) {
		this.time = time;
	}
	public String toString(){
		return JSON.toJSONString(this);
	}
	
}
