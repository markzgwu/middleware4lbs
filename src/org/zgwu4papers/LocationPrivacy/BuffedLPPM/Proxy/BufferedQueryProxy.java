package org.zgwu4papers.LocationPrivacy.BuffedLPPM.Proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.queries.kNN.*;
import org.zgwu4lab.lbs.queries.request.RequestUser;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.BufferManager;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.BufferWorker;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone.CellData;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.Issuers.RandomQuerySimulator;

public final class BufferedQueryProxy extends AbsProxy{
	
	int countingUsers = 1;
	int k = 5;
	
	void requests(){
		RandomQuerySimulator sim = new RandomQuerySimulator(countingUsers);
		sim.Generator();
		//sim.check();
		this.users = sim.QueryRequests;
		System.out.println("Request Pool:"+users.size());
	}

	public void cloaking(){
		RandomQuerySimulator sim = new RandomQuerySimulator(k-countingUsers);
		sim.Generator();
		//System.out.println("Request Pool:"+users.size());
		anonymousSet = new HashSet<RequestUser>();
		anonymousSet.addAll(sim.QueryRequests);
		//anonymousSet.addAll(users);
	}
	
	final POIDBserver poidbserver = new POIDBserver();
	public void obtainCandicateResults(){
		final BufferManager task = BufferWorker.getBuffer();
		
		this.candicateResults = new HashMap<String,POIentry>();
		
		for(RequestUser r:anonymousSet){
			ArrayList<String> grids = GridsUtils.computeGrids(r);
			for(String gridid:grids){
				
				CellData celldata = null;
				//System.out.println(task.check(gridid));
				if(task.check(gridid)){
					celldata = task.read(gridid);
				}else{
					celldata = poidbserver.packageCellData(gridid);
					//System.out.println(gridid+":"+celldata.getPOIs().size());
					//System.out.println(celldata);
					//task.write(gridid, celldata);
				}
				
				candicateResults.putAll(celldata.getPOIs());
				
			}
			
		}
		
		
		System.out.println("candicateResults:"+candicateResults.size());
		
		this.exactResults = new HashMap<String,POIentry>();
		for(RequestUser r:users){
			ArrayList<String> grids = GridsUtils.computeGrids(r);
			for(String gridid:grids){
				
				CellData celldata = null;
				//System.out.println(task.check(gridid));
				if(task.check(gridid)){
					celldata = task.read(gridid);
				}else{
					celldata = poidbserver.packageCellData(gridid);
					//System.out.println(gridid+":"+celldata.getPOIs().size());
					//System.out.println(celldata);
					//task.write(gridid, celldata);
				}
				
				this.exactResults.putAll(celldata.getPOIs());
				
			}
			
		}
		System.out.println("exactResults:"+exactResults.size());

	}
	
	public void obtainExactResults(){
		for(RequestUser r:users){
			NNQuery test = new NNQuery(r);
			test.loaddata(this.exactResults);
			long time1 = System.currentTimeMillis();
			int results_num = test.execute();
			long time2 = System.currentTimeMillis();
			test.ShowResults();
			System.out.println("NNquery Request Loc:"+r.location.toLocation_yx()+";# of POIs:"+results_num+";Time:"+(time2-time1) + " ms");		
		}
	}
	
	public void metric(){
		System.out.println();
		System.out.println("anonymity degree:"+k);
		System.out.println("the probability of inference attack:"+(1/(double)k));
		System.out.println();
	}
	
	public static void main(String[] args) {
		BufferedQueryProxy proxy = new BufferedQueryProxy();
		proxy.requests();
		proxy.cloaking();
		proxy.obtainCandicateResults();
		proxy.obtainExactResults();
		proxy.show();
		System.out.println("ending!");
	}

}
