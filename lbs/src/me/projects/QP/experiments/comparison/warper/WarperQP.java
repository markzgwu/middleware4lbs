package me.projects.QP.experiments.comparison.warper;

import me.projects.LISG.methods.AbsDataTrainer;
import me.projects.LISG.methods.DataTrainer5;
import me.projects.LISG.methods.LocationSemanticModel;

import org.projects.measurement.Measurement;
import org.projects.preprocessing.LoadingMO;
import org.projects.schemes.AbsScheme;
import org.tools.forMatlab.Format4Matlab;

public class WarperQP extends AbsWarper4QP {

	public WarperQP(AbsScheme scheme) {
		super(scheme);
		init();
	}

	public void execute(){
		scheme.init(datasampler.getRectenv());
		scheme.mount(dbsp);
		scheme.mount(bufferzone);
		scheme.mount(varLSM);
		scheme.mount(CRChecker);
		scheme.mount(TarReqRec);
		scheme.setup(TargetObjectID);
		//System.out.println("CRChecker="+JSON.toJSONString(CRChecker));
		//String results = "";
		//String[] tags = {"1","5","10","19","19"};
		//String[] tags = {"1"};
		final String[] tags = this.TimeStamps;
		final LoadingMO data = this.datasampler.getData();
		for(int i=0;i<tags.length;i++){
			final String timestamp = tags[i];
			final String[] dataset = data.loaddata_bytags(timestamp);
			//System.out.println("a snopshot batch:"+dataset.length+";"+JSONObject.toJSONString(dataset));
			System.out.println("TIMESTAMP="+timestamp+";Dataset Length:"+dataset.length);
			snopshot(dataset);
		}
		output();
		//System.out.println(results);
	}
	
	private String[] argumentarray;
	private String[] outputarray;
	void init(){
		argumentarray = new String[]{
				"time_",
				"demand_","dl_","ratio_",
				"SemanticRisk_",
				"QPsafety_",
				"AverQPsafety_",
				"cloakingfailure_",
				"time1_",
				"availcount_",
				"averageentropy_"
				};
		outputarray = new String[argumentarray.length];
		for(int i=0;i<outputarray.length;i++){
			outputarray[i] = "";
		}
			
	}
	LocationSemanticModel varLSM = null;
	public void load(){
		//AbsDataTrainer dataTrainer = new DataTrainer3(this.datasampler.getLevel());
		//final AbsDataTrainer dataTrainer = new DataTrainer5(this.datasampler);
		//varLSM = new LocationSemanticModel(dataTrainer);
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
		//System.out.println("snopshot:clear");
		scheme.mount(datasetsnapshot);
		//System.out.println("snopshot:start");
		scheme.download();

		outputarray[0]+=Measurement.toMSfromNS(scheme.elapsedTime)+",";//获取消耗时间  
		
		outputarray[1]+=scheme.demandcells+",";
		outputarray[2]+=scheme.downloadcells+",";
		//outputarray[2+a]+=(double)bufferedNewcasper.downloadcells/(double)bufferedNewcasper.demandcells+",";
		outputarray[3]+=(double)scheme.demandcells/(double)scheme.downloadcells+",";
		//logger.info(MyCloaking.getClass().getName()+" ending!");
		outputarray[4]+=scheme.risk+",";
		
		outputarray[5]+=scheme.QPSTAT.safety+",";
		outputarray[6]+=(double)scheme.QPSTAT.safety/(double)scheme.demandcells+",";
		
		outputarray[7]+=scheme.cloakingfailure+",";
		
		outputarray[8]+=Measurement.toMSfromNS(scheme.elapsedTime1)+",";
		outputarray[9]+=scheme.QPSTAT.availcount+",";
		outputarray[10]+=scheme.QPSTAT.totalentropy/(double)scheme.QPSTAT.availcount+",";
		//outputarray[5]+=scheme.QPSTAT.+",";
		
		
		//outputarray[4]+=varLSM.PosteriorBelief_Pr_A_Oloc(scheme.cellids)+",";
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
