package org.zgwu4lab.lbs.queries.rangequery;

import java.util.*;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.datamodel.json.JsonIndexEntry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4lab.lbs.index.grid.GridEncoder;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class IndexCircularRangequery4MidSrv extends AbsRangeQuery{

	public HashMap<String,POIentry> POIDB = null;
	public HashMap<String,JsonIndexEntry> POIIndex = null;
	
	public void loaddata(){
		POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
		POIIndex = SingletonPOILoader.INSTANCE.getLocalStorage_POIs_Index();
		System.out.println("DB Size:"+POIDB.size()+";Index Size:"+POIIndex.size());		
	}
	
	public IndexCircularRangequery4MidSrv(RequestUser request) {
		super(request);
	}
	
	public HashMap<String,POIentry> Candidate(){
		final RequestUser r = this.requestuser;
		final HashMap<String,POIentry> candidate_pois = new HashMap<String,POIentry>();
		//ArrayList<String> grids = GridEncoder.grids(r.location.toJsonLocation(), r.argument.value);
		
		ArrayList<String> grids =  GridEncoder.grids_range(r);
		
		logger.debug("# of Grids:"+grids.size());
		
		HashSet<String> temp = new HashSet<String>();
		
		for(String griduid:grids){
			boolean b = temp.add(griduid);
			if(!b){
				System.out.println(griduid);
			}
			//System.out.println(griduid);
			JsonIndexEntry index = POIIndex.get(griduid);
			if(index==null){
				
			}else{
				//System.out.println(index);
				String POIarray = index.getPOIUidArray();
				//System.out.println(POIarray);
				String[] uids = POIarray.split(",");
				for(String uid:uids){
					POIentry poi = POIDB.get(uid);
					candidate_pois.put(uid,poi);
					//System.out.println(uid+":"+poi);
				}
			}

		}
		
		logger.debug("candidate_pois SIZE:"+temp.size());
		return candidate_pois;
		
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
		
		final double whthin_radius = 0.01;
		r.argument = new QueryArgument("radius_degree" , whthin_radius);
		
		IndexCircularRangequery4MidSrv test = new IndexCircularRangequery4MidSrv(r);
		test.loaddata();
		long time1 = System.currentTimeMillis();
		int results_num = test.execute();
		long time2 = System.currentTimeMillis();
		test.ShowResults();
		System.out.println("# of POIs:"+results_num+";Time:"+(time2-time1)  + " ms");
	}
}
