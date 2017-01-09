package org.zgwu4papers.LocationPrivacy.BuffedLPPM.Proxy;

import java.util.HashSet;

import org.zgwu4lab.lbs.queries.kNN.*;
import org.zgwu4lab.lbs.queries.request.RequestUser;
import org.zgwu4papers.LocationPrivacy.BuffedLPPM.Issuers.RandomQuerySimulator;

public final class SimpleQueryProxy extends AbsProxy{
	
	int countingUsers = 1;
	int k = 10;
	
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
		anonymousSet.addAll(users);
	}
	
	public void obtainCandicateResults(){
		for(RequestUser r:anonymousSet){
			AbsNNQuery test = new IndexNNQuery(r);
			//AbsNNQuery test = new NNQuery(r);
			test.loaddata();
			long time1 = System.currentTimeMillis();
			int results_num = test.execute();
			long time2 = System.currentTimeMillis();
			test.ShowResults();
			System.out.println("NNquery Request Loc:"+r.location.toLocation_yx()+";# of POIs:"+results_num+";Time:"+(time2-time1) + " ms");		
		}
	}
	
	public void obtainExactResults(){
		
	}
	
	public void metric(){
		System.out.println();
		System.out.println("anonymity degree:"+k);
		System.out.println("the probability of inference attack:"+(1/(double)k));
		System.out.println();
	}
	
	public static void main(String[] args) {
		SimpleQueryProxy proxy = new SimpleQueryProxy();
		proxy.requests();
		proxy.cloaking();
		proxy.obtainCandicateResults();
		proxy.show();
		
	}

}
