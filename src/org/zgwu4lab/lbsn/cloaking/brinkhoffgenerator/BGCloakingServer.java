package org.zgwu4lab.lbsn.cloaking.brinkhoffgenerator;

import java.util.ArrayList;

import org.examples.v2.quadtree.impl.QuadTree;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.queries.request.RequestUser;

import com.alibaba.fastjson.JSON;

public final class BGCloakingServer {

	static void test1(){
		BGParser task = new BGParser();
		task.init();
		System.out.println(task.Data.size());
		ArrayList<BGEntry> l = task.filterbyUserid("0");
		System.out.println(l.size());
		String msg = task.printMassage(l);
		System.out.println(msg);
		
		ArrayList<BGEntry> l1 = task.filterbyTime("0");
		System.out.println(l1.size());
		String msg1 = task.printMassage(l1);
		System.out.println(msg1);
	}
	
	static void test2(){
		BGParser task = new BGParser();
		task.init();
		BGEntry queryuser1 = task.filterbyUseridAndTime("0","0");
		RequestUser q = BGTools.converttoRequestUser(queryuser1);
		
		ArrayList<BGEntry> l1 = task.filterbyTime("0");
		System.out.println(l1.size());
		//String msg1 = task.printMassage(l1);
		//System.out.println(msg1);
		
		ArrayList<POIentry> snapshot = BGTools.converttoPOIentry(l1);
		
		ArrayList<POIentry> initanonymityset = new ArrayList<POIentry>();
		for(POIentry onepoi:snapshot){
			double distance = MapsTool.mapmeasure.getDistance(q.location, onepoi.getLocation());
			if(distance<8){
				System.out.println(distance+";"+JSON.toJSONString(onepoi));
				initanonymityset.add(onepoi);
			}
		}
		
		System.out.println(initanonymityset.size());
		
		QuadTree qt = new QuadTree(-180.000000, -90.000000, 180.000000, 90.000000);
		
		for(POIentry onepoi:initanonymityset){
	        qt.set(onepoi.getLocation().lng_x, onepoi.getLocation().lat_y, onepoi);
		}
		
		System.out.println(qt.getCount());
		
		for(POIentry onepoi:initanonymityset){
	        System.out.println(qt.contains(onepoi.getLocation().lng_x, onepoi.getLocation().lat_y));
	        Object tmppoi = qt.get(onepoi.getLocation().lng_x, onepoi.getLocation().lat_y, null);
	        System.out.println(tmppoi.toString());
		}		
		
		/*
		while (!queue.isEmpty()) {
		      System.out.println(JSON.toJSONString(queue.poll()));
	    }
	    */
		
	}	
	
	public static void main(String[] args) {
		test2();
	}

}