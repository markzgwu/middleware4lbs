package org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone;

import java.io.Serializable;
import java.util.HashMap;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import com.alibaba.fastjson.JSON;

public final class CellData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CellID = "";
	final HashMap<String,POIentry> POIs = new HashMap<String,POIentry>();
	
	public String getCellID() {
		return CellID;
	}

	public void setCellID(String cellID) {
		CellID = cellID;
	}
	
	public HashMap<String, POIentry> getPOIs() {
		return POIs;
	}

	public String toString(){
		return JSON.toJSONString(this);
	}

}
