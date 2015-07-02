package org.experiments.comparison.warper;

import org.projects.measurement.Measurement;
import org.projects.schemes.AbsScheme;

public class SchemeWarper4Snapshot extends AbsSchemeWarper {

	public SchemeWarper4Snapshot(AbsScheme scheme) {
		super(scheme);
	}

	public void execute(){

		String results = "";
		//String[] tags = {"1","5","10","19","19"};
		String[] tags = {"0"};
		//String[] tags = TagsBatch.getContinousTimestamps(20);
		
		for(int i=0;i<tags.length;i++){
			final String timestamp = tags[i];
			final String[] dataset = data.loaddata_bytags(timestamp);
			//System.out.println("TIMESTAMP="+timestamp+";Dataset Length:"+dataset.length);
			snopshot(dataset);
		}
		output();
		System.out.println(results);
	}
	
	public String prefix = "";
	final String name = scheme.getClass().getSimpleName();
	Dataoutput dataoutput;
	public void mount(final Dataoutput dataoutput){
		this.dataoutput = dataoutput;
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
		dataoutput.outputarray[3]+=Measurement.toMSfromNS(elapsedTime)+",";
		
		dataoutput.outputarray[0]+=scheme.demandcells+",";
		dataoutput.outputarray[1]+=scheme.downloadcells+",";
		//outputarray[2+a]+=(double)bufferedNewcasper.downloadcells/(double)bufferedNewcasper.demandcells+",";
		dataoutput.outputarray[2]+=(double)scheme.demandcells/(double)scheme.downloadcells+",";
		dataoutput.outputarray[4]+=(double)scheme.downloadcells/(double)scheme.demandcells+",";
		//logger.info(MyCloaking.getClass().getName()+" ending!");	
		scheme.clear();
	}

	@Override
	public String output() {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
