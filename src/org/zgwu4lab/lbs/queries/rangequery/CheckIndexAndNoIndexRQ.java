package org.zgwu4lab.lbs.queries.rangequery;

import java.util.HashMap;
import java.util.HashSet;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.mobileclient.RandomClientSimulator;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class CheckIndexAndNoIndexRQ {
	
	static HashMap<String,POIentry> POIDB = null;
	
	public static HashSet<RequestUser> getReq(){
		RandomClientSimulator sim = new RandomClientSimulator();
		sim.Generator();
		sim.check();
		final HashSet<RequestUser> r_pool = sim.QueryRequests;
		System.out.println("Request Pool:"+r_pool.size());
		return r_pool;
	}	
	
	public static HashSet<RequestUser> getReq1(){
		RequestUser r = new RequestUser();
		//r.location = new LocationCoordinate("40.009237,116.321344");
		//r.location = new LocationCoordinate("40.000279,116.309039");//chang chun yuan
		//r.location = new LocationCoordinate("40.001279,116.309039");
		//r.location = new LocationCoordinate("40.068391,116.404078");//tian tong yuan
		r.location = new LocationCoordinate("39.83025,116.288423");//
		//r.location = new LocationCoordinate("40.044729,116.991864");
		//r.location = new LocationCoordinate("40.009021,116.321625");
		r.request_id = "20081023025304";
		r.request_data = "GeoLIfe";
		HashSet<RequestUser> r_pool = new HashSet<RequestUser>();
		r.argument = new QueryArgument("radius_degree" , 0.005);
		r_pool.add(r);
		System.out.println("Request Pool:"+r_pool.size());
		return r_pool;
	}
	
	public static HashMap<String, String> index(RequestUser req){
		System.out.print("INDEX MODE:");
		IndexCircularRangequery4MidSrv test = new IndexCircularRangequery4MidSrv(req);
		test.loaddata();
		POIDB = test.POIDB;
		//System.out.print("DB Size:"+test.POIDB.size()+";Index Size:"+test.POIIndex.size());
		long time1 = System.currentTimeMillis();
		int results_num = test.execute();
		long time2 = System.currentTimeMillis();
		test.ShowResults();
		System.out.println("# of POIs:"+results_num+";Time:"+(time2-time1)  + " ms");
		return test.results_POIs;
	}
	
	public static HashMap<String, String> noindex(RequestUser req){
		System.out.print("NO INDEX MODE:");
		CircularRangequery4MidSrv test = new CircularRangequery4MidSrv(req);
		test.loaddata();
		long time1 = System.currentTimeMillis();
		int results_num = test.execute();
		long time2 = System.currentTimeMillis();
		test.ShowResults();
		System.out.println("# of POIs:"+results_num+";Time:"+(time2-time1) + " ms");
		return test.results_POIs;
	}
	
	public static void check(HashMap<String, String> m1, HashMap<String, String> m_large){
		for(String uid:m_large.keySet()){
			boolean b = m1.containsKey(uid);
			if(!b){
				System.out.println(uid+";"+POIDB.get(uid).getData());
			}
		}
	}
	
	public static void main(String[] args) {
		HashSet<RequestUser> r_pool = getReq1();
		//HashMap<String, String> result_index1 = index(r_pool);
		for(RequestUser req:r_pool){
			System.out.println("radius_degree:"+req.argument.value+";Location:"+req.location.toLocation_yx());
			HashMap<String, String> result_noindex = noindex(req);
			HashMap<String, String> result_index = index(req);
			check(result_index,result_noindex);
			
		}
		
	}

}
