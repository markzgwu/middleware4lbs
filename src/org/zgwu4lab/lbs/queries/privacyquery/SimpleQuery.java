package org.zgwu4lab.lbs.queries.privacyquery;

import java.util.HashMap;
import java.util.HashSet;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.mobileclient.RandomClientSimulator;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class SimpleQuery {
	static HashMap<String,POIentry> POIDB = null;
	
	public static HashSet<RequestUser> getReq(){
		RandomClientSimulator sim = new RandomClientSimulator();
		sim.Generator();
		//sim.check();
		final HashSet<RequestUser> r_pool = sim.QueryRequests;
		System.out.println("Request Pool:"+r_pool.size());
		return r_pool;
	}
	
	public static void main(String[] args) {
		HashSet<RequestUser> r_pool = getReq();
		//HashMap<String, String> result_index1 = index(r_pool);
		for(RequestUser req:r_pool){
			System.out.println(req.username+";"+req.argument.toString()+";Location:"+req.location.toLocation_yx());
		}
		
	}

}
