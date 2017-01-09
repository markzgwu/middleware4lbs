package org.zgwu4lab.lbs.queries.rangequery;

import java.util.HashMap;

import org.tools.forLog.AbsLog;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public abstract class AbsRangeQuery extends AbsLog{
	public AbsRangeQuery(RequestUser request){
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
	
	public HashMap<String,String> within_rangequery(){
		return within_rangequery(Candidate());
	}
	
	protected HashMap<String,String> within_rangequery(final HashMap<String,POIentry> POIs){
		final HashMap<String,String> results_POIs = new HashMap<String,String>();
		final RequestUser r = this.requestuser;
		double radius_km = MapsTool.mapmeasure.toKMfromlngOffset(r.location, r.argument.value);
		for(POIentry onepoi: POIs.values()){
			double distance = MapsTool.mapmeasure.getDistance(r.location, onepoi.getLocation());
			//System.out.println("distance="+distance+";"+radius_km+";"+onepoi.getData());
			if(distance<=radius_km){
				results_POIs.put(onepoi.getUid(), ""+distance);
			}
		}
		return results_POIs;
	}	
}
