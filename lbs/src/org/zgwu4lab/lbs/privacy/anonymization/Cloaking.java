package org.zgwu4lab.lbs.privacy.anonymization;

import java.util.ArrayList;
import java.util.HashMap;

import org.zgwu4lab.lbs.framework.mobileclient.ClientSimulatorJson;
import org.zgwu4lab.lbs.index.grid.GridEncoder;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public class Cloaking {

	public static void main(String[] args) {
		ClientSimulatorJson sim = new ClientSimulatorJson();
		sim.Generator();
		//sim.show();
		HashMap<String,Integer> reqindex = new HashMap<String,Integer>();
		ArrayList<String> reqarray = sim.QueryRequests;
		for(String json:reqarray){
			//System.out.println(json);
			RequestUser u = RequestUser.fromJson(json);
			String grid = GridEncoder.leftbuttom(u.location.toJsonLocation());
			System.out.println(grid+":"+u.location.toLocation_yx());
			
			if(reqindex.containsKey(grid)){
				reqindex.put(grid, reqindex.get(grid)+1);
			}else{
				reqindex.put(grid, 1);
			}
			
		}
		
		for(String grid:reqindex.keySet()){
			System.out.println(grid+":"+reqindex.get(grid));
		}
		
	}

}
