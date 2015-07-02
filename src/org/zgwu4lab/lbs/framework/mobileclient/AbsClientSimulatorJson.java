package org.zgwu4lab.lbs.framework.mobileclient;

import java.util.ArrayList;

public abstract class AbsClientSimulatorJson extends AbsClientSimulator {
	final public ArrayList<String> QueryRequests = new ArrayList<String>();
	public void show() {
		for(String json:QueryRequests){
			System.out.println(json);
		}
	}	
	
}
