package measure.bayes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forLog.ILog;
import org.tools.forString.StringTool;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public class Bayes4LBS implements ILog{

	LoadingMO data = null;
	RectEnv rectenv = null;
	Rectangle rect = null;
	int level = 2;
	
	void init(){
		String filepath = "d:\\";
		//final String filepath = "..";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		data = new LoadingMO(filepath);
		final String tag = "20";
		rect = data.rectangelTrim(tag);

	}
	
	final int max_objectid = 1199+1;
	
	double worker(){
		
		rectenv = new RectEnv(level,rect);
		logger.info(rectenv.summary());
		
		ArrayList<String[]> database =  new ArrayList<String[]>();
		for(int id=0;id<max_objectid;id++){
			String objectid = String.valueOf(id);
			String[] locationset = data.loaddata_byobjectids(objectid);
			//System.out.println(StringTool.ShowStringArray(locationset));
			
			//String[] locationCidSet = new String[locationset.length];
			//database.add(locationCidSet);
			
			int i = 0;
			HashSet<String> set = new HashSet<String>();
			for(String oneline:locationset){
				MovingObject mo = new MovingObject(oneline);
				String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);

				set.add(cellid);
				//locationCidSet[i++] = cellid;
				//set.add(e)
			}
			String[] locationCidSet = new String[set.size()];
			locationCidSet = set.toArray(locationCidSet);
			database.add(locationCidSet);
			//System.out.println(id+": "+StringTool.ShowStringArray(locationCidSet));
			
		}
		
		
		final Classifier cf = new Classifier();
		for(int id=0;id<max_objectid;id++){
			String objectid = String.valueOf(id);		
			cf.train(database.get(id), objectid);
		}
		
		double ratio;
		double success_count = 0;
		double total_count = 0;
		for(int id=0;id<max_objectid;id++){
			String[] e= database.get(id);
			//System.out.println(e);
			//System.out.println(id+": "+StringTool.ShowStringArray(e));
			String r = cf.getClassification(e);
			//System.out.println("get type = "+r);
			boolean b_success = r.equals(String.valueOf(id));
			total_count++;
			if (b_success){
				success_count++;
			}
			//System.out.println(success_count/total_count);
		}
		return success_count/total_count;
	}
	
	public static void main(String[] args) {
		Bayes4LBS task = new Bayes4LBS();
		task.init();
		double result = 0;
		String output = "";
		for(int l=2;l<13;l++){
			task.level = l;
			result = task.worker();
			output+=result+",";
		}
		System.out.println(output);
	}

}
