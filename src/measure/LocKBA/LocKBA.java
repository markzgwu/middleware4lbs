package measure.LocKBA;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import measure.LocKBA.model.ObjectAtTag;
import measure.LocKBA.model.SnopshotStatistics;
import measure.bayes.Classifier;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class LocKBA implements ILog{

		LoadingMO data = null;
		RectEnv rectenv = null;
		Rectangle rect = null;
		int level = 2;
		
		void init(){
			String filepath = "d:\\";
			//final String filepath = "..";
			filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
			//filepath+=File.separator+"_workshop"+File.separator+"1336.txt";
			
			data = new LoadingMO(filepath);
			final String tag = "20";
			rect = data.rectangelTrim(tag);

		}
		
		final int max_objectid = 9+1;
		
		double GetLR(String sampleObjectid,String targetObjectid){
			double LR = 1;
			
			int max_tag = 5;
			rectenv = new RectEnv(level,rect);
			logger.info(rectenv.summary());

			//final String sampleObjectid = "0";
			//final String targetObjectid = "1";
			
			//for(int id=0;id<max_objectid;id++){
				//String objectid = String.valueOf(id);
				//String[] sampleuserset = data.loaddata_byobjectids(objectid);			
				//ArrayList<String[]> database =  new ArrayList<String[]>();
				for(int tag=0;tag<max_tag;tag++){
					final String tagstr = String.valueOf(tag);
					double LR1 = parse(sampleObjectid,targetObjectid,tagstr);
					LR = LR * LR1;
				}
				//}
			//}
			return LR;
		}
		
		double parse(String sampleObjectid,String targetObjectid,String tagstr){
			//ObjectAtTag objectAtTag = null;
			double LR1 = 1;
			final String[] lines = data.loaddata_bytags(tagstr);
			//final String[] cellids = new String[lines.length];
			//System.out.println("lines.length:"+lines.length);
			final Frequency freq = new Frequency();
			
			String samplecellid = null;
			String targetcellid = null;
			
			for(String oneline:lines){
				MovingObject mo = new MovingObject(oneline);
				final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
				//System.out.println(cellid);
				freq.addValue(cellid);
				if(sampleObjectid.equals(mo.getObject_Id())){
					samplecellid = cellid;
				}
				if(targetObjectid.equals(mo.getObject_Id())){
					targetcellid = cellid;
				}

			}
			
			double P1 = PrEventTrue(samplecellid,targetcellid);
			double P2 = PrEventFalse(samplecellid,targetcellid,freq);
			LR1 = LR1 * (P1/P2);
			//System.out.println("LR1="+LR1);
			//objectAtTag = new ObjectAtTag(samplecellid,targetcellid,freq);
			//return objectAtTag;
			return LR1;
		}
		
		double PrEventTrue(String samplecellid,String targetcellid){
			double PrEventTrue = 0;
			double const_source_availability = 0.01;
			if(samplecellid.equals(targetcellid)){
				PrEventTrue = const_source_availability;
			}else{
				PrEventTrue = 1-const_source_availability;
			}
			//System.out.println("PrEventTrue="+PrEventTrue);
			return PrEventTrue;
		}
		
		
		
		double PrEventFalse(String samplecellid,String targetcellid,final Frequency freq){
			
			double PrEventFalse = 0;
			//final SnopshotStatistics stat = new SnopshotStatistics();
			//stat.mount(cellids);
			//final Frequency freq = stat.getFrequency();
			double p = freq.getPct(samplecellid);
			//double g = p*p+(1-p)*(1-p);
			//double g = 0.5;
			//PrEventFalse = g;
			///*
			Iterator<Entry<Comparable<?>, Long>> m = freq.entrySetIterator();
			double gx = 0;
			while(m.hasNext()){
				String cellid=(String)m.next().getKey();
				double px = freq.getPct(cellid);
				gx += px * px;
			}
			
			double g = gx;
			///*
			if(samplecellid.equals(targetcellid)){
				PrEventFalse = g;
			}else{
				PrEventFalse = 1-g;
			}
			//*/
			//PrEventFalse = g;
			//*/
			//System.out.println("PrEventFalse="+PrEventFalse+";p="+p+";g="+g);
			return PrEventFalse;
		}
		
		String worker(String title){
			String result = "";
			String sampleobjid = "6";
			String targetobjid = "";
			for(int i=0;i<20;i++){
				targetobjid = String.valueOf(i);
				double lr = GetLR(sampleobjid,targetobjid);
				lr = Math.log10(lr);
				result += lr+",";
			}
			
			result = Format4Matlab.output4matlab(title, result);
			
			return result;
		}
		
		public static void main(String[] args) {
			LocKBA task = new LocKBA();
			task.init();
			//double result = 0;
			String result = "";
			String output = "";
			for(int l=4;l<7;l++){
				task.level = l;
				result = task.worker("L"+l);
				output+=result+"\n";
			}
			System.out.println(output);
		}
}
