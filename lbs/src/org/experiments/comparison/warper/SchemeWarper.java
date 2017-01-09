package org.experiments.comparison.warper;

import org.projects.measurement.Measurement;
import org.projects.preprocessing.TagsBatch;
import org.projects.schemes.*;
import org.tools.forMatlab.Format4Matlab;

public class SchemeWarper extends AbsSchemeWarper {

	public SchemeWarper(AbsScheme scheme) {
		super(scheme);
		init();
	}

	public void execute(){

		String results = "";
		//String[] tags = {"1","5","10","19","19"};
		//String[] tags = {"1"};
		String[] tags = TagsBatch.getContinousTimestamps(20);
		
		for(int i=0;i<tags.length;i++){
			final String timestamp = tags[i];
			final String[] dataset = data.loaddata_bytags(timestamp);
			//System.out.println("TIMESTAMP="+timestamp+";Dataset Length:"+dataset.length);
			snopshot(dataset);
		}
		//output();
		System.out.println(results);
	}
	
	private String[] argumentarray;
	private String[] outputarray;
	void init(){
		argumentarray = new String[]{
				"demand_","dl_","ratio_",
				"time_",
				};
		outputarray = new String[argumentarray.length];
		for(int i=0;i<outputarray.length;i++){
			outputarray[i] = "";
		}		
	}
	
	
	public String output(){
		final String name = scheme.getClass().getSimpleName();	
		for(int i=0;i<argumentarray.length;i++){
			final String output = prefix+Format4Matlab.output4matlab(argumentarray[i]+name, outputarray[i]);
			System.out.println(output);
		}
		return null;
	}	
	
	private void snopshot(final String[] datasetsnapshot){
		//System.out.println(datasetsnapshot+";"+datasetsnapshot.length);
		scheme.init(rectenv);
		scheme.mount(datasetsnapshot);
		scheme.mount(dbsp);
		scheme.mount(bufferzone);
		
		long startTime=System.nanoTime();   //获取开始时间 
		scheme.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		outputarray[3]+=Measurement.toMSfromNS(elapsedTime)+",";
		
		outputarray[0]+=scheme.demandcells+",";
		outputarray[1]+=scheme.downloadcells+",";
		//outputarray[2+a]+=(double)bufferedNewcasper.downloadcells/(double)bufferedNewcasper.demandcells+",";
		outputarray[2]+=(double)scheme.demandcells/(double)scheme.downloadcells+",";
		//logger.info(MyCloaking.getClass().getName()+" ending!");	
		scheme.clear();
	}	
	
	public void summary(){
		{
			long startTime=System.nanoTime();   //获取开始时间 
			bufferzone.check("123456");
			//bufferzone1.save("123456", "123456");
			//bufferzone1.load("123456");
			long endTime=System.nanoTime(); //获取结束时间  
			long elapsedTime = (endTime-startTime);
			System.out.println("time cost of cache one unit: chech"+Measurement.toMSfromNS(elapsedTime));
		}
		{
			long startTime=System.nanoTime();   //获取开始时间 
			//bufferzone1.check("123456");
			bufferzone.save("123456", "123456");
			//bufferzone1.load("123456");
			long endTime=System.nanoTime(); //获取结束时间  
			long elapsedTime = (endTime-startTime);
			System.out.println("time cost of cache one unit: save"+Measurement.toMSfromNS(elapsedTime));
		}
		{
			long startTime=System.nanoTime();   //获取开始时间 
			//bufferzone1.check("123456");
			//bufferzone1.save("123456", "123456");
			bufferzone.load("123456");
			long endTime=System.nanoTime(); //获取结束时间  
			long elapsedTime = (endTime-startTime);
			System.out.println("time cost of cache one unit: load"+Measurement.toMSfromNS(elapsedTime));
		}		
		{
			long startTime=System.nanoTime();   //获取开始时间 
			dbsp.retrieval("123456");
			long endTime=System.nanoTime(); //获取结束时间  
			long elapsedTime = (endTime-startTime);
			System.out.println("time cost of downloading one unit:"+Measurement.toMSfromNS(elapsedTime));			
		}
	}	
	
}
