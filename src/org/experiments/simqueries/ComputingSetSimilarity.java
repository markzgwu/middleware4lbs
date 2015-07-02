package org.experiments.simqueries;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.projects.queries.complex.SetSimilarity;
import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class ComputingSetSimilarity implements ILog {
	
	LoadingMO dataloader = null;
	Rectangle rect = null;
	
	public void init(){
		String filepath = "d:\\";
		//final String filepath = "..";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		dataloader = new LoadingMO(filepath);
		final String tag = "20";
		rect = dataloader.rectangelTrim(tag);
	}
	
	public HashSet<String> convert(String[] datasource,RectEnv rectenv){	

		HashSet<String> result = new HashSet<String>();
		for(String oneline:datasource){
			MovingObject mo = new MovingObject(oneline);
			String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);				
			result.add(cellid);
		}
		return result; 
	}
	int level;
	String result = "";
	String tmpresult = "";
	String tmpresult1 = "";
	public void worker(){
		
		final RectEnv rectenv = new RectEnv(level,rect);
		logger.info(rectenv.summary());
		
		HashSet<String> SetA = convert(dataloader.loaddata_byobjectids("0"),rectenv);
		HashSet<String> SetB = convert(dataloader.loaddata_byobjectids(label),rectenv);
		System.out.println(SetA);
		System.out.println(SetB);
		
		SetSimilarity sim = new SetSimilarity(SetA, SetB);
		//String result = label+":"+sim.getJI();
		tmpresult+=sim.getJI()+",";
		tmpresult1+=SetB.size()+",";
		//System.out.println(result);
		/*
		if(sim.getJI()>0){
			topk.add(result);
		}
		*/
	}

	final public ArrayList<String> topk = new ArrayList<String>();
	
	String label = null;
	
	public void execute(){
		
		init();
		/*
		for(int i=1;i<1000;i++){
		label = ""+i;
		worker();
		}
		*/
		
		//String[] labelarray = {"52","65","801","109","888"};
		String[] labelarray = {"0","52","65","801","109","888"};
		for(String tmplabel:labelarray){
			
			System.out.println(tmplabel+"[START]");
			
			tmpresult = "";
			tmpresult1 = "";
			label = tmplabel;
			//result += label+"=";
			
			for(int i=1;i<19;i++){	
				level = i;
				worker();
			}
			
			System.out.println(tmplabel+"[END]");
			//result += Format4Matlab.output4matlab("label"+label, tmpresult)+"\n";
			result += Format4Matlab.output4matlab("label"+label, tmpresult1)+"\n";
		}
		
		System.out.println(result);
		
	}
	
	public static void main(String[] args) {

		ComputingSetSimilarity task = new ComputingSetSimilarity();
		task.execute();
		
		/*
		for(String e:task.topk){
			System.out.println(e);
		}
		
		System.out.println(task.topk.size());
		*/

	}

}
