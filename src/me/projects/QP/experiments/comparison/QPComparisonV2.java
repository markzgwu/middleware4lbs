package me.projects.QP.experiments.comparison;

import me.projects.LISG.methods.DataSampler;
import me.projects.LISG.methods.ParameterTable;
import me.projects.LISG.methods.Parameters;
import me.projects.QP.experiments.comparison.warper.WarperQP;
import me.projects.QP.methods.*;
import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.TargetReguestRecorder;

import org.projects.caches.ICache;
import org.projects.preprocessing.TagsBatch;
import org.projects.privacymodel.PrivacyProfile;
import org.projects.providers.IDBProvider;
import org.projects.providers.ProviderSimple;
import org.projects.schemes.*;
import org.tools.forLog.ILog;

import com.alibaba.fastjson.JSON;

public final class QPComparisonV2 implements ILog{
	
	Parameters para = null;
	DataSampler datasampler = null;
	CRPrediction CRChecker = null;
	
	final IDBProvider dbsp1 = new ProviderSimple();
	final ICache bufferzone = null;	
	
	String output="";
	PrivacyProfile PrivacyProfile = null;
	String[] TargetObjectIDs = null;
	String[] TimeStamps = null;

	public QPComparisonV2(){
		init();
	}
	
	void init(){
	
	para = new Parameters(5);
	ParameterTable.INSTANCE.init(para);
	datasampler = ParameterTable.INSTANCE.getDatasampler();
	CRChecker = new CRPrediction(datasampler);
	TargetObjectIDs = TagsBatch.getTags(50);
	TimeStamps = TagsBatch.getTags(20);
	}
	
	public void test1(){

		PrivacyProfile = new PrivacyProfile(10,2,0.1,1);
		String prefix = "STAT_";
		System.out.println("Setup Successfully!\n");
		//System.gc();
		{
		final WarperQP schemewarper = new WarperQP(new QPPredictionProtection(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.mount(CRChecker);
		final TargetReguestRecorder TarReqRec= new TargetReguestRecorder();
		schemewarper.mount(TarReqRec);
		
		schemewarper.define(TimeStamps,TargetObjectIDs);
		
		schemewarper.prefix = prefix;
		schemewarper.load();
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		
		//QPOfflineAnalyzer QPOA = new QPOfflineAnalyzer(TarReqRec, CRChecker);
		//output+=QPOA.analyze("0")+"\n";
		//QPOA.show();
		
		System.out.println("------------------------------------------------");
		
		}		
	}
	
	public void test2(){
		PrivacyProfile = new PrivacyProfile(10,2,0.1,2);
		//String output="";
		
		String prefix = "STAT_";
		System.out.println("Setup Successfully!\n");
		//System.gc();		
		
		{
		final WarperQP schemewarper = new WarperQP(new QPPredictionProtection(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.mount(CRChecker);
		final TargetReguestRecorder TarReqRec= new TargetReguestRecorder();
		schemewarper.mount(TarReqRec);
		
		schemewarper.define(TimeStamps,TargetObjectIDs);
		
		schemewarper.load();
		schemewarper.prefix = prefix;
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		
		//QPOfflineAnalyzer QPOA = new QPOfflineAnalyzer(TarReqRec, CRChecker);
		//output+="\n"+QPOA.analyze("0")+"\n";
		//QPOA.show();
		
		System.out.println("------------------------------------------------");
		
		}
		//System.gc();
		
		
	}	
		
	public void test3(){
		PrivacyProfile = new PrivacyProfile(10,2,0.1,3);
		//String output="";
		
		String prefix = "STAT_";
		System.out.println(JSON.toJSONString(para));
		System.out.println("Setup Successfully!\n");
		//System.gc();		
		
		{
		final WarperQP schemewarper = new WarperQP(new QPPredictionProtection(PrivacyProfile));
		schemewarper.mount(datasampler);
		schemewarper.mount(dbsp1,bufferzone);
		schemewarper.mount(CRChecker);
		final TargetReguestRecorder TarReqRec= new TargetReguestRecorder();
		schemewarper.mount(TarReqRec);
		
		schemewarper.define(TimeStamps,TargetObjectIDs);
		
		schemewarper.load();
		schemewarper.prefix = prefix;
		schemewarper.execute();
		output+=schemewarper.output()+"\n";
		
		//QPOfflineAnalyzer QPOA = new QPOfflineAnalyzer(TarReqRec, CRChecker);
		//output+="\n"+QPOA.analyze("0")+"\n";
		//QPOA.show();
		
		System.out.println("------------------------------------------------");
		
		}
		//System.gc();
		
		
	}	
	
	public static void main(String[] args) {
		QPComparisonV2 me = new QPComparisonV2();
		System.out.println(JSON.toJSONString(me.para));
		System.out.println(JSON.toJSONString(me.PrivacyProfile));
		
		me.test1();
		me.test2();
		me.test3();
		
		System.out.println(me.output);
		
		//System.out.println(CacheTable.INSTANCE.CachePostBeliefs);
		System.out.println(CacheTable.INSTANCE.CachePostBeliefs.size());
		System.out.println(CacheTable.INSTANCE.ZeroPostBeliefsInSet.size());
		
	}
	
}
