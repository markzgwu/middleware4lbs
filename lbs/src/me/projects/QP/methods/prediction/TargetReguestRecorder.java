package me.projects.QP.methods.prediction;

import java.util.ArrayList;
import java.util.HashMap;

import me.projects.toolkit.ToolkitRecorder;

public final class TargetReguestRecorder {

	public final HashMap<String,ArrayList<CRHistory>> recorder = new HashMap<String,ArrayList<CRHistory>>();
	
	public void add(final CRHistory oneCRHistory){
		final String key = oneCRHistory.TargetID;
		if(recorder.containsKey(key)){
			recorder.get(key).add(oneCRHistory);
		}else{
			final ArrayList<CRHistory> newCRhistory = new ArrayList<CRHistory>();
			newCRhistory.add(oneCRHistory);
			recorder.put(key, newCRhistory);
		}
	}
	
	public ArrayList<String[]> getRecent(final String ObjectID,final int recent_length){
		return ToolkitRecorder.getRecent(recorder.get(ObjectID), recent_length);
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
