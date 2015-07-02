package org.zgwu4lab.lbs.datamodel.json;

public class JsonIndexEntry {
	String GridUid;
	public final String getGridUid() {
		return GridUid;
	}
	public final void setGridUid(String gridUid) {
		GridUid = gridUid;
	}
	public final String getPOIUidArray() {
		return POIUidArray;
	}
	public final void setPOIUidArray(String pOIUidArray) {
		POIUidArray = pOIUidArray;
	}
	String POIUidArray;
}
