package measure.LocKBA.experiments;

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

public final class LocEntropy implements ILog{

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
	
		double parse(String tagstr){
			//ObjectAtTag objectAtTag = null;
			double LR1 = 1;
			final String[] lines = data.loaddata_bytags(tagstr);
			//final String[] cellids = new String[lines.length];
			//System.out.println("lines.length:"+lines.length);
			final Frequency freq = new Frequency();
			
			for(String oneline:lines){
				MovingObject mo = new MovingObject(oneline);
				final String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);
				//System.out.println(cellid);
				freq.addValue(cellid);

			}

			Iterator<Entry<Comparable<?>, Long>> m = freq.entrySetIterator();
			double entropy = 0;
			while(m.hasNext()){
				String cellid=(String)m.next().getKey();
				double px = freq.getPct(cellid);
				double pa = Math.log10(px)/Math.log10(2);
				double pb = px * pa;
				entropy += pb;
			}
			entropy = -entropy;
			return entropy;
		}
		
		String worker(String title){
			String result = "";
			int max_tag = 20;
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
					double entropy = parse(tagstr);
					result+=entropy+",";
				}
				//}
			//}
			
			result = Format4Matlab.output4matlab(title, result);
			
			return result;
		}
		
		public static void main(String[] args) {
			LocEntropy task = new LocEntropy();
			task.init();
			//double result = 0;
			String result = "";
			String output = "";
			for(int l=2;l<5;l++){
				task.level = l;
				result = task.worker("L"+l);
				output+=result+"\n";
			}
			System.out.println(output);
		}
}
