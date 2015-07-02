package me.experiments.HilbertIndex;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class POIindexFromMO {
	//ArrayList<Integer> poiindex = new ArrayList<Integer>();
	public Integer[] poiindex;
	
	LoadingMO data = null;
	RectEnv rectenv = null;
	Rectangle rect = null;
	int level = 5;
	
	void init1(){
		String filepath = "d:\\";
		//final String filepath = "..";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		//filepath+=File.separator+"_workshop"+File.separator+"1336.txt";
		
		data = new LoadingMO(filepath);
		final String tag = "20";
		rect = data.rectangelTrim(tag);
		
	}
	
	void init2(){
		rectenv = new RectEnv(level,rect);	
		System.out.println(rectenv.summary());
	}
	
	Frequency freq = new Frequency();
	
	ArrayList<Integer> PIR(int hid_start,int hid_end){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(Integer poi:poiindex){
			boolean b = (poi>=hid_start) && (poi<=hid_end);
			if(b){
				result.add(poi);
			}
		}
		return result;
	}
	
	Integer[] getPOIdata(){
		HashSet<Integer> poiindex2 = new HashSet<Integer>();
		
		String tagstr = "20";
		final String[] lines = data.loaddata_bytags(tagstr);
		for(String oneline:lines){
			MovingObject mo = new MovingObject(oneline);
			//final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
			final int hid = MovingObjectBatch.mapping4hilbertid(mo, rectenv);
			//System.out.println("cellid="+cellid+";hid="+hid);
			poiindex2.add(hid);
			
		}
		
		final TreeSet<Integer> poiindex3 = new TreeSet<Integer>(poiindex2);
		poiindex3.comparator();
		
		Integer[] poiindex = new Integer[poiindex3.size()];
		poiindex = poiindex3.toArray(poiindex);	
		return poiindex;
	}	
	
	public void init(){
		init1();
		init2();
		poiindex = getPOIdata();
	}
	
	public Integer[] getPOIdataRandom(){
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
		POIindexFromMO poiindex = new POIindexFromMO();
		poiindex.init();
		//poiindex.poiindex;

	}

}
