package org.zgwu4lab.lbsn.cloaking.brinkhoffgenerator;

import java.util.ArrayList;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class BGTools {

	public static RequestUser converttoRequestUser(BGEntry e){
		RequestUser u = new RequestUser();
		u.time = e.time;
		u.username = e.userid;
		LocationCoordinate loc = new LocationCoordinate();
		loc.fromLocation_yx(e.latitude+","+e.longitude);
		u.location = loc;
		u.request_id = "unknown";
		u.argument = null;
		return u;
	}
	
	public static ArrayList<RequestUser> converttoRequestUser(ArrayList<BGEntry> l){
		ArrayList<RequestUser> r = new ArrayList<RequestUser>();
		for(BGEntry e:l){
			r.add(converttoRequestUser(e));
		}
		return r;
	}
	
	public static POIentry converttoPOIentry(BGEntry e){
		POIentry u = new POIentry();
		u.setTime(e.time);
		u.setName(e.userid);
		LocationCoordinate loc = new LocationCoordinate();
		loc.fromLocation_yx(e.latitude+","+e.longitude);
		u.setLocation(loc);
		//u.setData(JSON.toJSONString(e));
		u.setData(null);
		u.setAddress(null);
		u.setInfo(null);
		return u;
	}	
	
	public static ArrayList<POIentry> converttoPOIentry(ArrayList<BGEntry> l){
		ArrayList<POIentry> r = new ArrayList<POIentry>();
		for(BGEntry e:l){
			r.add(converttoPOIentry(e));
		}
		return r;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
