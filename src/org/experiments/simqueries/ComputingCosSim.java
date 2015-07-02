package org.experiments.simqueries;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.Convertor;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.MovingObject;
import org.projects.preprocessing.MovingObjectBatch;
import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class ComputingCosSim implements ILog {
	
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
	
	public Frequency convert(String[] datasource,RectEnv rectenv){	

		Frequency result = new Frequency();
		for(String oneline:datasource){
			MovingObject mo = new MovingObject(oneline);
			String cellid = MovingObjectBatch.mapping4cellid(mo, rectenv);				
			result.addValue(cellid);
		}
		return result; 
	}
	
	public double getArrayValue(String index,Frequency freq){
		return freq.getPct(index);
		//return getArrayValue1(index,freq);
		//return getArrayValue2(index,freq);
	}
	
	double getArrayValue1(String index,Frequency freq){
		double result  = freq.getCount(index);
		//System.out.println(freq.hashCode()+":"+index+":"+result);
		return result; 
	}
	
	double getArrayValue2(String index,Frequency freq){
		double result  = freq.getCount(index);
		if(result>0){
			result = 1;
		}
		return result; 
	}
	
	double power2(double a){
		//return Math.pow(a, 2);
		return a*a;
	}
	
	public long getZero(String index,Frequency freq){
		return 0;
	}
	
	public double similarity2(Frequency freq1,Frequency freq2){
		double a = dotmulti(freq1,freq2);
		double b = length(freq1)*length(freq2);
		double result = a/b;
		return result;
	}
	
	public double dotmulti(Frequency freq1,Frequency freq2){
		double result = 0;
		for(String cid:cidlist){
			double a = getArrayValue(cid,freq1)*getArrayValue(cid,freq2);
			result+=a;
		}
		return result;
	}
	
	public double length(Frequency freq){
		double result = 0;
		for(String cid:cidlist){
			double a = power2(getArrayValue(cid,freq));
			result+=a;
		}
		result = Math.sqrt(result);
		return result;
	}
	
	ArrayList<String> cidlist = null;
	
	public double distance(Frequency freq1,Frequency freq2){
		double result = 0;
		
		for(String cid:cidlist){
			double a = getArrayValue(cid,freq1)-getArrayValue(cid,freq2);
			double b = power2(a);
			result+=b;
		}
		result = Math.sqrt(result);
		return result;
	}
	
	int level;
	String result = "";
	String tmpresult = "";
	String tmpresult1 = "";
	public void worker(){
		
		final RectEnv rectenv = new RectEnv(level,rect);
		logger.info(rectenv.summary());
		
		Frequency FreqA = convert(dataloader.loaddata_byobjectids("0"),rectenv);
		Frequency FreqB = convert(dataloader.loaddata_byobjectids(label),rectenv);
		System.out.println(FreqA);
		System.out.println(FreqB);
		
		//double sim = similarity1(FreqA, FreqB);
		double sim = similarity2(FreqA, FreqB);
		//String result = label+":"+sim.getJI();
		tmpresult+=sim+",";
		//tmpresult1+=SetB.size()+",";
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
			
			for(int i=1;i<11;i++){	
				level = i;
				cidlist = Convertor.extend("", level);
				System.out.println("cidlist.size()="+cidlist.size());
				worker();
			}
			
			System.out.println(tmplabel+"[END]");
			result += Format4Matlab.output4matlab("label"+label, tmpresult)+"\n";
			//result += Format4Matlab.output4matlab("label"+label, tmpresult1)+"\n";
		}
		
		System.out.println(result);
		
	}
	
	public static void main(String[] args) {

		ComputingCosSim task = new ComputingCosSim();
		task.execute();
		
		/*
		for(String e:task.topk){
			System.out.println(e);
		}
		
		System.out.println(task.topk.size());
		*/

	}

}
