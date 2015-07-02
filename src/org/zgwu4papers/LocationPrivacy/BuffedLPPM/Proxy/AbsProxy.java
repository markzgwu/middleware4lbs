package org.zgwu4papers.LocationPrivacy.BuffedLPPM.Proxy;

import java.util.HashMap;
import java.util.HashSet;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public abstract class AbsProxy {
	protected HashSet<RequestUser> users = null;
	protected HashSet<RequestUser> anonymousSet = null;
	protected HashMap<String,POIentry> candicateResults = null;
	protected HashMap<String,POIentry> exactResults = null;
	
	abstract void requests();
	abstract void cloaking();
	abstract void obtainCandicateResults();
	abstract void obtainExactResults();
	
	void show(HashSet<RequestUser> allusers){
		if(allusers == null) return;
		for(RequestUser req:allusers){
			System.out.println(req.username+";"+req.argument.toString()+";Location:"+req.location.toLocation_yx());
		}		
	}
	
	void show(HashMap<String,POIentry> allpois){
		if(allpois == null) return;
		for(POIentry poi:allpois.values()){
			System.out.println(poi.toString());
		}		
	}
	
	abstract void metric();
	
	void show(){
		
		show(users);
		show(anonymousSet);
		//show(candicateResults);
		//show(exactResults);
		
		metric();
	}

}
