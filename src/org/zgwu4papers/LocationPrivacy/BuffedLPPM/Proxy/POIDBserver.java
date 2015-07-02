package org.zgwu4papers.LocationPrivacy.BuffedLPPM.Proxy;

import java.util.HashMap;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.datamodel.json.JsonIndexEntry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.CellData;

public final class POIDBserver {
	final HashMap<String,POIentry> POIDB;
	final HashMap<String,JsonIndexEntry> POIIndex;
	
	public POIDBserver(){
		POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
		POIIndex = SingletonPOILoader.INSTANCE.getLocalStorage_POIs_Index();
	}
	
	CellData packageCellData(String cellid){
		CellData celldata = new CellData();
		celldata.setCellID(cellid);
		//System.out.println("Loading "+celldata);
		JsonIndexEntry jsonpoiindex = POIIndex.get(cellid);
		if (jsonpoiindex==null) return celldata;
		
		String POIarray = jsonpoiindex.getPOIUidArray();
		//System.out.println(POIarray);
		String[] uids = POIarray.split(",");
		for(String uid:uids){
			POIentry poi = POIDB.get(uid);
			celldata.getPOIs().put(uid,poi);
			//System.out.println(uid+":"+poi);
		}
		return celldata;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
