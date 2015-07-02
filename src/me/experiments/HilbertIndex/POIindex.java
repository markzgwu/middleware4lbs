package me.experiments.HilbertIndex;

import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public final class POIindex {
	//ArrayList<Integer> poiindex = new ArrayList<Integer>();
	public Integer[] poiindex;
	
	public void init(){
		poiindex = getPOIdata();
	}
	
	public Integer[] getPOIdata(){
		HashSet<Integer> poiindex2 = new HashSet<Integer>();
		int limit = 1024;
		Random random = new Random();
		
		for(int i=0;i<200;i++){
			Integer a = random.nextInt()/limit;
			poiindex2.add(a);
		}
		
		final TreeSet<Integer> poiindex3 = new TreeSet<Integer>(poiindex2);
		poiindex3.comparator();
		
		Integer[] poiindex = new Integer[poiindex3.size()];
		poiindex = poiindex3.toArray(poiindex);
		
		return poiindex;
	}
	
	public static void main(String[] args) {
		POIindex poiindex = new POIindex();
		poiindex.init();
		//poiindex.poiindex;

	}

}
