package org.zgwu4lab.lbs.framework.middletier;

import java.util.ArrayList;
import java.util.HashMap;

import org.tools.forLog.AbsLog;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class Middleserver extends AbsLog{
	final public ArrayList<RequestUser> request_pool;
	final public HashMap<String,POIentry> POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
	final double whthin_radius = 0.02;
	
	public Middleserver(ArrayList<RequestUser> request_pool){
		this.request_pool = request_pool;
	}
	
	public int within_rangequery(){
		int results_num = 0;
		for(RequestUser r :request_pool){
			for(POIentry onepoi: POIDB.values()){
				double distance = MapsTool.mapmeasure.getDistance(r.location, onepoi.getLocation());
				logger.debug("distance="+distance);
				if(distance<=whthin_radius){
					results_num++;
					System.out.println("distance="+distance+";"+onepoi.getData());
				}
			}
		}
		return results_num;
	}
	
	public int execute(){
		return within_rangequery();
	}
}
