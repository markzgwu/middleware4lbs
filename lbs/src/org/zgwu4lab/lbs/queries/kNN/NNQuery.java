package org.zgwu4lab.lbs.queries.kNN;

import java.util.HashSet;
import java.util.HashMap;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4lab.lbs.framework.mobileclient.RandomClientSimulator;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class NNQuery extends AbsNNQuery{

	public HashMap<String,POIentry> POIDB = null;
	
	public void loaddata(){
		loaddata(SingletonPOILoader.INSTANCE.getLocalStorage_POIs());	
	}
	
	public void loaddata(HashMap<String,POIentry> ArgPOIDB){
		POIDB = ArgPOIDB;
		System.out.println("DB Size:"+POIDB.size());
	}
	
	public NNQuery(RequestUser request) {
		super(request);
	}
	
	public int execute(){
		results_POIs = doNNquery();
		return results_POIs.size();
	}

	public void ShowResults(){
		final HashMap<String,String> results = results_POIs;
		for(String uid:results.keySet()){
			String msg = "Distacne:"+results.get(uid)+"km;"+POIDB.get(uid).getData();
			System.out.println(msg);
		}
	}
	
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
		r.location = new LocationCoordinate("40.068391,116.404078");//tian tong yuan
		//r.location = new LocationCoordinate("39.83025,116.288423");//
		//r.location = new LocationCoordinate("40.044729,116.991864");
		//r.location = new LocationCoordinate("40.009021,116.321625");
		r.request_id = "20081023025304";
		r.request_data = "GeoLIfe";
		HashSet<RequestUser> r_pool = new HashSet<RequestUser>();
		r.argument = new QueryArgument("radius_degree",0.005);
		r_pool.add(r);
		System.out.println("Request Pool:"+r_pool.size());
		return r_pool;
	}
	
	public static void main(String[] args){

		HashSet<RequestUser> r_pool = getReq();
		//System.out.println("Request Pool:"+r_pool.size());
		
		for(RequestUser r:r_pool){
			NNQuery test = new NNQuery(r);
			test.loaddata();
			long time1 = System.currentTimeMillis();
			int results_num = test.execute();
			long time2 = System.currentTimeMillis();
			test.ShowResults();
			System.out.println("NNquery Request Loc:"+r.location.toLocation_yx()+";# of POIs:"+results_num+";Time:"+(time2-time1) + " ms");		
		}

	}

	@Override
	public HashMap<String, POIentry> Candidate() {
		return POIDB;
	}	
}
