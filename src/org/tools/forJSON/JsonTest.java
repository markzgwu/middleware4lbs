package org.tools.forJSON;

import java.util.ArrayList;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;

import com.alibaba.fastjson.JSON;

public final class JsonTest {
	public static void test1(){
		
		ArrayList<POIentry> l = new ArrayList<POIentry>();
		for(int i=0;i<10;i++){
			POIentry poi = new POIentry();
			poi.setUid("UID"+i);
			l.add(poi);
		}
		
		String jsonString = JSON.toJSONString(l);
		 
		System.out.println(jsonString);		
	}

	public static void test2(String jsonString){
		
		POIentry poi = JSON.parseObject(jsonString, POIentry.class);
		System.out.println(poi.getUid());

	}
	
	public static void main(String[] args) {
		test1();
		//String jsonString = "\"{\"uid\":\"UID0\"}\"";
		POIentry poi = new POIentry();
		poi.setUid("UID");
		String jsonString = JSON.toJSONString(poi);
		System.out.println(jsonString);
		test2(jsonString);
	}
	
	
}
