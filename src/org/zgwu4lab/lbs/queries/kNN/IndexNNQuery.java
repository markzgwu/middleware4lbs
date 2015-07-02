package org.zgwu4lab.lbs.queries.kNN;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.datamodel.json.JsonIndexEntry;
import org.zgwu4lab.lbs.framework.database.memory.SingletonPOILoader;
import org.zgwu4lab.lbs.framework.mobileclient.RandomClientSimulator;
import org.zgwu4lab.lbs.index.grid.GridEncoder;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class IndexNNQuery extends AbsNNQuery{

	public HashMap<String,POIentry> POIDB = null;
	public HashMap<String,JsonIndexEntry> POIIndex = null;
	
	public void loaddata(){
		POIDB = SingletonPOILoader.INSTANCE.getLocalStorage_POIs();
		POIIndex = SingletonPOILoader.INSTANCE.getLocalStorage_POIs_Index();
	}
	
	public void loaddata(HashMap<String,JsonIndexEntry> ArgPOIIndex ,HashMap<String,POIentry> ArgPOIDB){
		POIIndex = ArgPOIIndex;
		POIDB = ArgPOIDB;
		System.out.println("DB Size:"+POIDB.size()+";Index Size:"+POIIndex.size());	
	}
	
	public IndexNNQuery(RequestUser request) {
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
		r.argument = new QueryArgument("radius_degree", 0.005);
		r_pool.add(r);
		System.out.println("Request Pool:"+r_pool.size());
		return r_pool;
	}
	
	public static void main(String[] args){

		HashSet<RequestUser> r_pool = getReq();
		//System.out.println("Request Pool:"+r_pool.size());
		
		for(RequestUser r:r_pool){
			IndexNNQuery test = new IndexNNQuery(r);
			test.loaddata();
			long time1 = System.currentTimeMillis();
			int results_num = test.execute();
			long time2 = System.currentTimeMillis();
			test.ShowResults();
			System.out.println("NNquery Request Loc:"+r.location.toLocation_yx()+";# of POIs:"+results_num+";Time:"+(time2-time1) + " ms");		
		}

	}

	public ArrayList<String> computeGrids(){
		final RequestUser req = this.requestuser;
		ArrayList<String> grids = null;
		double r;
		for(int j=0;j<20;j++){
			r = j*I_constant.index_grid_granularity;
			grids = GridEncoder.grids_range(req.location.toJsonLocation(),r);
			//System.out.println("# of Grids"+grids.size());
			if(Check(grids)>0) {
				r = (j+1)*I_constant.index_grid_granularity;
				grids = GridEncoder.grids_range(req.location.toJsonLocation(),r);				
				break;
			}
		}
		//System.out.println("# of Grids"+grids.size());
		return grids;
	}
	
	@Override
	public HashMap<String, POIentry> Candidate() {
		return Candidate(computeGrids());
	}
	
	private int Check(ArrayList<String> grids) {
		int b = 0;
		for(String griduid:grids){
			JsonIndexEntry index = POIIndex.get(griduid);
			if(index!=null){
				b++;
			}
		}
		//System.out.println(b+" of "+grids.size());
		return b;
	}
	
	private HashMap<String, POIentry> Candidate(ArrayList<String> grids) {

		final HashMap<String,POIentry> candidate_pois = new HashMap<String,POIentry>();
		
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

}
