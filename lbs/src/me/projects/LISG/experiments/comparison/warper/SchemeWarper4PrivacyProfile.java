package me.projects.LISG.experiments.comparison.warper;

import me.projects.LISG.methods.*;

import org.projects.measurement.Measurement;
import org.projects.preprocessing.LoadingMO;
import org.projects.preprocessing.TagsBatch;
import org.projects.schemes.*;
import org.tools.forMatlab.Format4Matlab;

public class SchemeWarper4PrivacyProfile extends AbsSchemeWarper4PrivacyMetric {

	public SchemeWarper4PrivacyProfile(AbsScheme scheme) {
		super(scheme);
		init();
	}

	public void execute(){

		String results = "";
		//String[] tags = {"1","5","10","19","19"};
		//String[] tags = {"1"};
		final String[] tags = TagsBatch.getContinousTimestamps(20);
		LoadingMO data = this.datasampler.getData();
		double count = 0;
		for(int i=0;i<tags.length;i++){
			count++;
			final String timestamp = tags[i];
			final String[] dataset = data.loaddata_bytags(timestamp);
			//System.out.println("TIMESTAMP="+timestamp+";Dataset Length:"+dataset.length);
			snopshot(dataset);
		    StatPara.total_cloaking_success_ratio+=StatPara.cloaking_success_ratio;
		}
		StatPara.average_cloaking_success_ratio=StatPara.total_cloaking_success_ratio/count;
		output();
		System.out.println(results);
	}
	
	private String[] argumentarray;
	private String[] outputarray;
	void init(){
		argumentarray = new String[]{
				"time_",
				"demand_","dl_","ratio_",
				"risk_",
				"cloaking_failure_","cloaking_success_","cloaking_success_ratio_"
				};
		outputarray = new String[argumentarray.length];
		for(int i=0;i<outputarray.length;i++){
			outputarray[i] = "";
		}
			
	}
	LocationSemanticModel varLSM = null;
	public void load(){
		//AbsDataTrainer dataTrainer = new DataTrainer3(this.datasampler.getLevel());
		final AbsDataTrainer dataTrainer = new DataTrainer5(this.datasampler);
		varLSM = new LocationSemanticModel(dataTrainer);
	}
	
	
	public String output(){
		String output = "";
		final String name = scheme.getClass().getSimpleName();	
		for(int i=0;i<argumentarray.length;i++){
			output += prefix+Format4Matlab.output4matlab(argumentarray[i]+name, outputarray[i])+"\n";
			//System.out.println(output);
		}
		return output;
	}	
	
	private void snopshot(final String[] datasetsnapshot){
		//System.out.println(datasetsnapshot+";"+datasetsnapshot.length);
		scheme.init(this.datasampler.getRectenv());
		scheme.mount(datasetsnapshot);
		scheme.mount(dbsp);
		scheme.mount(bufferzone);
		scheme.mount(varLSM);
		
		long startTime=System.nanoTime();   //获取开始时间 
		scheme.download();
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		outputarray[0]+=Measurement.toMSfromNS(elapsedTime)+",";
		
		outputarray[1]+=scheme.demandcells+",";
		outputarray[2]+=scheme.downloadcells+",";
		//outputarray[2+a]+=(double)bufferedNewcasper.downloadcells/(double)bufferedNewcasper.demandcells+",";
		outputarray[3]+=(double)scheme.demandcells/(double)scheme.downloadcells+",";
		//logger.info(MyCloaking.getClass().getName()+" ending!");
		outputarray[4]+=scheme.risk+",";
		outputarray[5]+=scheme.cloakingfailure+",";
		final int claking_success = (scheme.demandcells-scheme.cloakingfailure);
		outputarray[6]+=claking_success+",";
		StatPara.cloaking_success_ratio = (double)claking_success/(double)scheme.demandcells;
		outputarray[7]+=StatPara.cloaking_success_ratio+",";
		//outputarray[4]+=varLSM.PosteriorBelief_Pr_A_Oloc(scheme.cellids)+",";
		scheme.clear();
	}	
	
	public void summary(){
		{
			
			
		}

	}	
	
}
