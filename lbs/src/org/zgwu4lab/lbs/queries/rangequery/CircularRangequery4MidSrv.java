package org.zgwu4lab.lbs.queries.rangequery;

import java.util.ArrayList;
import java.util.HashMap;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class CircularRangequery4MidSrv extends AbsRangeQuery{

	public HashMap<String,POIentry> POIDB = null;
	
	public void loaddata(){
		POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
		System.out.println("DB Size:"+POIDB.size());		
	}
	
	public CircularRangequery4MidSrv(RequestUser request) {
		super(request);
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
	
	public static void main(String[] args){
		RequestUser r = new RequestUser();
		//r.location = new LocationCoordinate("40.009237,116.321344");
		r.location = new LocationCoordinate("40.000279,116.309039");//chang chun yuan
		//r.location = new LocationCoordinate("40.009021,116.321625");
		r.request_id = "20081023025304";
		r.request_data = "GeoLIfe";
		
		ArrayList<RequestUser> r_pool = new ArrayList<RequestUser>();
		
		r.argument = new QueryArgument("radius_degree" , 0.01);
		r_pool.add(r);
		
		System.out.println("Request Pool:"+r_pool.size());
		CircularRangequery4MidSrv test = new CircularRangequery4MidSrv(r);
		test.loaddata();
		long time1 = System.currentTimeMillis();
		int results_num = test.execute();
		long time2 = System.currentTimeMillis();
		test.ShowResults();
		System.out.println("# of POIs:"+results_num+";Time:"+(time2-time1) + " ms");
	}

	@Override
	public HashMap<String, POIentry> Candidate() {
		// TODO Auto-generated method stub
		return POIDB;
	}	
}
