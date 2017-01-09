package org.projects.preprocessing;

public class TagsBatch {
	public static String[] getContinousTimestamps(int maxtag){
		return getTags(maxtag);
	}
	
	public static String[] getContinousObjectIDs(int maxobjectid){
		return getTags(maxobjectid);
	}
	
	public static String[] getTags(final int num){
		String[] ObjectIDs= new String[num];
		for(int i=0;i<num;i++){
			ObjectIDs[i] = String.valueOf(i);
		}
		return ObjectIDs;
	}
	
	public static String[] getTags(final int start, final int num){
		String[] ObjectIDs= new String[num];
		for(int i=0;i<num;i++){
			ObjectIDs[i] = String.valueOf(start+i);
		}
		return ObjectIDs;
	}	
	
}
