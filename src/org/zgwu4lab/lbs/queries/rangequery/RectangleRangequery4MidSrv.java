package org.zgwu4lab.lbs.queries.rangequery;

import java.util.HashMap;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class RectangleRangequery4MidSrv extends AbsRangeQuery{

	public RectangleRangequery4MidSrv(RequestUser request) {
		super(request);
	}

	public HashMap<String,POIentry> POIDB = null;
	
	public void loaddata(){
		POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
	}
	
	public int execute(){
		results_POIs = within_rangequery();
		return results_POIs.size();
	}
	
	public void ShowResults(){
		final HashMap<String,String> results = results_POIs;
		for(String uid:results.keySet()){
			String msg = "distacne="+results.get(uid)+";"+POIDB.get(uid).getData();
			System.out.println(msg);
		}
	}

	@Override
	public HashMap<String, POIentry> Candidate() {
		return POIDB;
	}	
}
