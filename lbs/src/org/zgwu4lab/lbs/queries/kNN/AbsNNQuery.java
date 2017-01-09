package org.zgwu4lab.lbs.queries.kNN;

import java.util.HashMap;
import java.util.HashSet;

import org.tools.forLog.AbsLog;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public abstract class AbsNNQuery extends AbsLog {
	public AbsNNQuery(RequestUser request){
		this.requestuser = request;
	}
	
	final public RequestUser requestuser;
	protected HashMap<String,String> results_POIs = null;
	public final HashMap<String, String> getResults_POIs() {
		return results_POIs;
	}

	public abstract int execute();
	public abstract void loaddata();
	public abstract void ShowResults();
	public abstract HashMap<String,POIentry> Candidate();
	
	public HashMap<String,String> doNNquery(){
		return doNNquery(Candidate());
	}
	
	protected HashMap<String,String> doNNquery(final HashMap<String,POIentry> POIs){
		final HashMap<String,String> results_POIs = new HashMap<String,String>();
		final RequestUser r = this.requestuser;
		double history_distance = -1;
		//String history_uid = null;
		HashSet<String> history_uids = new HashSet<String>();
		for(POIentry onepoi: POIs.values()){
			double distance = MapsTool.mapmeasure.getDistance(r.location, onepoi.getLocation());

			if(history_distance<0){
				results_POIs.put(onepoi.getUid(), ""+distance);
				history_distance = distance;
				history_uids.add(onepoi.getUid());
			}else{
				//System.out.println(":"+(history_distance > distance));
				if(history_distance >= distance){
					//System.out.println("distance="+distance+";"+history_distance+";"+onepoi.getData());
					if(history_distance > distance){
						for(String history_uid:history_uids){
							results_POIs.remove(history_uid);
						}
					}
					/*
					if(history_distance == distance){
						
					}
					*/
					
					results_POIs.put(onepoi.getUid(), ""+distance);
					history_distance = distance;
					history_uids.add(onepoi.getUid());
				}

			}
			
			//System.out.println("distance="+distance+";"+history_distance+";"+onepoi.getData());
		}
		return results_POIs;
	}
}
