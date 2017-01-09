package me.projects.QP.experiments.comparison.warper;

import java.util.HashSet;

import me.projects.LISG.methods.DataSampler;
import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.TargetReguestRecorder;

import org.experiments.comparison.warper.Dataoutput;
import org.projects.caches.ICache;
import org.projects.providers.IDBProvider;
import org.projects.schemes.AbsScheme;

public abstract class AbsWarper4QP {
	final protected AbsScheme scheme;
	public AbsWarper4QP(final AbsScheme scheme){
		this.scheme = scheme;
	}
	
	protected IDBProvider dbsp = null;
	protected ICache bufferzone = null;
	public void mount(
			final IDBProvider dbsp,
			final ICache bufferzone){
		this.dbsp = dbsp;
		this.bufferzone = bufferzone;
	}
	
	protected DataSampler datasampler = null;
	public void mount(final DataSampler datasampler){
		this.datasampler = datasampler;
	}
	
	protected Dataoutput dataoutput;
	public void mount(final Dataoutput dataoutput){
		this.dataoutput = dataoutput;
	}
	
	protected CRPrediction CRChecker;
	public void mount(CRPrediction CRChecker){
		this.CRChecker = CRChecker;
	}
	
	//protected String name;
	public void load(){
		
	}
	public abstract void execute();
	public abstract String output();
	public String prefix = "";
	final public StatisticsParameters StatPara = new StatisticsParameters();

	public TargetReguestRecorder TarReqRec = null;
	public void mount(final TargetReguestRecorder oneTarReqRec){
		this.TarReqRec = oneTarReqRec; 
	}
	
	public HashSet<String> TargetObjectID = null;
	private void setup(final String[] TargetObjectIDs){
		this.TargetObjectID = new HashSet<String>();
		for(String TargetID:TargetObjectIDs){
			if(this.CRChecker.alltails.keySet().contains(TargetID)){
				TargetObjectID.add(TargetID);
			}
		}
	}
	
	public String[] TimeStamps = null;
	public void define(final String[] TimeStamps,final String[] TargetObjects){
		this.TimeStamps = TimeStamps;
		setup(TargetObjects);
	}
}
