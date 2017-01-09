package org.experiments.comparison;

import java.io.File;

import org.apache.commons.math3.stat.Frequency;
import org.projects.caches.CacheEhcache;
import org.projects.caches.ICache;
import org.projects.datamodel.RectEnv;
import org.projects.measurement.Measurement;
import org.projects.metrics.Statistics;
import org.projects.preprocessing.*;
import org.projects.privacymodel.PrivacyProfile;
import org.projects.providers.*;
import org.projects.schemes.*;
import org.tools.forLog.ILog;
import org.tools.forMatlab.Format4Matlab;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class Comparison implements ILog{
	
	private String[] argumentarray,timerarray;
	private String[] outputarray,timeroutputarray;
	
	public Comparison(){
		init();
	}
	
	void init(){
		
		
		argumentarray = new String[]{
				"demand_base","dl_base","ratio_base",
				"demand_newcasper","dl_newcasper","ratio_newcasper",
				"demand_buffering","dl_buffering","ratio_buffering",
				"demand_bufNC","dl_bufNC","ratio_bufNC",
				"demand_mycloaking","dl_mycloaking","ratio_mycloaking"
				};
		outputarray = new String[argumentarray.length];
		for(int i=0;i<outputarray.length;i++){
			outputarray[i] = "";
		}
		
		timerarray = new String[]{
				"timer_base",
				"timer_newcasper",
				"timer_buffering",
				"timer_bufNC",
				"timer_mycloaking"
				};
		timeroutputarray = new String[timerarray.length];
		for(int i=0;i<timeroutputarray.length;i++){
			timeroutputarray[i] = "";
		}
	}
	
	private RectEnv rectenv = null;
	private LoadingMO data = null;
	private IDBProvider dbsp = null;
	public void mount(final RectEnv rectenv,final LoadingMO data,final IDBProvider dbsp){
		this.rectenv = rectenv;
		this.data = data;
		this.dbsp = dbsp;
	}
	
	public void worker(){

		String results = "";
		//String[] tags = {"1","5","10","19","19"};
		//String[] tags = {"1"};
		String[] tags = TagsBatch.getContinousTimestamps(20);
		
		for(int i=0;i<tags.length;i++){
			final String timestamp = tags[i];
			final String[] dataset = data.loaddata_bytags(timestamp);
			System.out.println("TIMESTAMP="+timestamp+";Dataset Length:"+dataset.length);
			snopshot(dataset);
			
		}
		output();
		System.out.println(results);
	
	}

	final ICache bufferzone1 = new CacheEhcache("Buffer1");
	final ICache bufferzone2 = new CacheEhcache("Buffer2");
	final ICache bufferzone3 = new CacheEhcache("Buffer3");
	void clearbufferzone(){
		bufferzone1.clear();
		bufferzone1.clear();
		bufferzone1.clear();
	}
	
	void snopshot(String[] dataset){
		final PrivacyProfile privacyprofile= new PrivacyProfile(5,2,0);
		int a;
		{
		final AbsScheme baseline = new Baseline();
		baseline.init(rectenv);
		baseline.mount(dataset);
		baseline.mount(dbsp);
		final Frequency freq1 = new Frequency();
		baseline.mount(freq1);
		
		long startTime=System.nanoTime();   //获取开始时间 
		baseline.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		timeroutputarray[0]+=Measurement.toMSfromNS(elapsedTime)+",";
		
		outputarray[0]+=baseline.demandcells+",";
		outputarray[1]+=baseline.downloadcells+",";
		//outputarray[2]+=(double)baseline.downloadcells/(double)baseline.demandcells+",";
		outputarray[2]+=(double)baseline.demandcells/(double)baseline.downloadcells+",";
		//logger.info(baseline.getClass().getName()+" ending!");
		final Statistics stat = new Statistics();
		stat.mount(freq1);
		stat.mount(rectenv);
		//stat.output();
		//logger.info(baseline.getClass().getName()+" statistics!");
		
		}
		
		
		{
		final AbsScheme newcasper = new Newcasper(privacyprofile);
		newcasper.init(rectenv);
		newcasper.mount(dataset);
		newcasper.mount(dbsp);
		
		long startTime=System.nanoTime();   //获取开始时间 
		newcasper.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		timeroutputarray[1]+=Measurement.toMSfromNS(elapsedTime)+",";		

		
		a = 3;
		outputarray[0+a]+=newcasper.demandcells+",";
		outputarray[1+a]+=newcasper.downloadcells+",";
		//outputarray[2+a]+=(double)newcasper.downloadcells/(double)newcasper.demandcells+",";
		outputarray[2+a]+=(double)newcasper.demandcells/(double)newcasper.downloadcells+",";
		//logger.info(newcasper.getClass().getName()+" ending!");
		}
		
		{
		final AbsScheme buffering = new BufferingV2();
		buffering.init(rectenv);
		buffering.mount(dataset);
		buffering.mount(dbsp);
		buffering.mount(bufferzone1);
		
		long startTime=System.nanoTime();   //获取开始时间 
		buffering.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		timeroutputarray[2]+=Measurement.toMSfromNS(elapsedTime)+",";	
		
		
		a = 6;
		outputarray[0+a]+=buffering.demandcells+",";
		outputarray[1+a]+=buffering.downloadcells+",";
		//outputarray[2+a]+=(double)buffering.downloadcells/(double)buffering.demandcells+",";
		outputarray[2+a]+=(double)buffering.demandcells/(double)buffering.downloadcells+",";
		//logger.info(buffering.getClass().getName()+" ending!");
		}
		
		{
		final AbsScheme bufferedNewcasper = new BufferedNewcasper(privacyprofile);
		bufferedNewcasper.init(rectenv);
		bufferedNewcasper.mount(dataset);
		bufferedNewcasper.mount(dbsp);
		bufferedNewcasper.mount(bufferzone2);
		
		long startTime=System.nanoTime();   //获取开始时间 
		bufferedNewcasper.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		timeroutputarray[3]+=Measurement.toMSfromNS(elapsedTime)+",";
		
		a = 9;
		outputarray[0+a]+=bufferedNewcasper.demandcells+",";
		outputarray[1+a]+=bufferedNewcasper.downloadcells+",";
		//outputarray[2+a]+=(double)bufferedNewcasper.downloadcells/(double)bufferedNewcasper.demandcells+",";
		outputarray[2+a]+=(double)bufferedNewcasper.demandcells/(double)bufferedNewcasper.downloadcells+",";
		//logger.info(bufferedNewcasper.getClass().getName()+" ending!");
		}
		
		{
		final AbsScheme MyCloaking = new MyCloaking(privacyprofile);
		MyCloaking.init(rectenv);
		MyCloaking.mount(dataset);
		MyCloaking.mount(dbsp);
		MyCloaking.mount(bufferzone2);
		
		long startTime=System.nanoTime();   //获取开始时间 
		MyCloaking.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		timeroutputarray[4]+=Measurement.toMSfromNS(elapsedTime)+",";
		
		a = 12;
		outputarray[0+a]+=MyCloaking.demandcells+",";
		outputarray[1+a]+=MyCloaking.downloadcells+",";
		//outputarray[2+a]+=(double)bufferedNewcasper.downloadcells/(double)bufferedNewcasper.demandcells+",";
		outputarray[2+a]+=(double)MyCloaking.demandcells/(double)MyCloaking.downloadcells+",";
		//logger.info(MyCloaking.getClass().getName()+" ending!");
		}		
	}
	
	void output(){
		for(int i=0;i<argumentarray.length;i++){
			final String output = prefix+Format4Matlab.output4matlab(argumentarray[i], outputarray[i]);
			System.out.println(output);
		}
		
		for(int i=0;i<timerarray.length;i++){
			final String output = prefix+Format4Matlab.output4matlab(timerarray[i], timeroutputarray[i]);
			System.out.println(output);
		}		
		
	}
	
	
	public void summary(){
		{
			long startTime=System.nanoTime();   //获取开始时间 
			bufferzone1.check("123456");
			//bufferzone1.save("123456", "123456");
			//bufferzone1.load("123456");
			long endTime=System.nanoTime(); //获取结束时间  
			long elapsedTime = (endTime-startTime);
			System.out.println("time cost of cache one unit: chech"+Measurement.toMSfromNS(elapsedTime));
		}
		{
			long startTime=System.nanoTime();   //获取开始时间 
			//bufferzone1.check("123456");
			bufferzone1.save("123456", "123456");
			//bufferzone1.load("123456");
			long endTime=System.nanoTime(); //获取结束时间  
			long elapsedTime = (endTime-startTime);
			System.out.println("time cost of cache one unit: save"+Measurement.toMSfromNS(elapsedTime));
		}
		{
			long startTime=System.nanoTime();   //获取开始时间 
			//bufferzone1.check("123456");
			//bufferzone1.save("123456", "123456");
			bufferzone1.load("123456");
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
	
	private final String prefix = "slim_";
	public static void main(String[] args) {
		final IDBProvider dbsp1 = new ProviderSimple();
		//final IDBProvider dbsp = new ProviderBaiduMaps();
		final WorkerMysql mysql = new WorkerMysql();
		mysql.init();
		final IDBProvider dbsp = new ProviderMYSQL(mysql);
		
		String filepath = "d:\\";
		//final String filepath = "..";
		filepath+=File.separator+"_workshop"+File.separator+"1630.txt";
		final LoadingMO data = new LoadingMO(filepath);
		final int level = 5;
		final String tag = "20";
		final Rectangle rect = data.rectangelTrim(tag);
		final RectEnv rectenv = new RectEnv(level,rect);
		logger.info(rectenv.summary());
		
		///*
		{
		final Comparison comp = new Comparison();
		comp.mount(rectenv,data,dbsp1);
		comp.summary();
		comp.worker();
		}
		//*/
		
		{
		final Comparison comp = new Comparison();
		comp.mount(rectenv,data,dbsp1);
		comp.clearbufferzone();
		comp.summary();
		comp.worker();
		}

		mysql.close();
	}
	
}
