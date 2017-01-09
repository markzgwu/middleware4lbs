package me.experiments.HilbertIndex;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.ArrayTool;
import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;
import org.tools.forString.StringTool;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class BuildHilbertIndex implements ILog{

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
		
		public Integer[] poiindex;
		void init3(){
			POIindexFromMO poiindexclass = new POIindexFromMO();
			poiindexclass.init();
			poiindex = poiindexclass.poiindex;	
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
		
		void parse(){
			
			String tagstr = "0";
			final String[] lines = data.loaddata_bytags(tagstr);
			for(String oneline:lines){
				MovingObject mo = new MovingObject(oneline);
				final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
				final int hid = MovingObjectBatch.mapping4hilbertid(mo, rectenv);
				System.out.println("cellid="+cellid+";hid="+hid);
				int hid_start = hid;
				int hid_end = hid;
				ArrayList<Integer> r = PIR(hid_start,hid_end);
				System.out.println("kNN result:"+StringTool.ShowIntegerArray(r));
			}
		}
		
		int[] range(int hid,int k){
			int[] range = new int[2];
			
			int hid_start = hid-k;
			int hid_end = hid+k;
			
			return range;
		}
		
		final int max_objectid = 9+1;
		
		public void domain(){
			
			init1();
			init2();
			init3();
			parse();
			
		}
		
		public static void main(String[] args) {
			BuildHilbertIndex task = new BuildHilbertIndex();
			task.domain();

		}
}
