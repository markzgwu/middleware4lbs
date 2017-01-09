package me.projects.LISG.experiments.comparison.warper;

import me.projects.LISG.methods.DataSampler;

import org.experiments.comparison.warper.Dataoutput;
import org.projects.caches.ICache;
import org.projects.providers.IDBProvider;
import org.projects.schemes.AbsScheme;

public abstract class AbsSchemeWarper4PrivacyMetric {
	final protected AbsScheme scheme;
	public AbsSchemeWarper4PrivacyMetric(final AbsScheme scheme){
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
	
	Dataoutput dataoutput;
	public void mount(final Dataoutput dataoutput){
		this.dataoutput = dataoutput;
	}
	
	//protected String name;
	public void load(){
		
	}
	public abstract void execute();
	public abstract String output();
	public String prefix = "";
	final public StatisticsParameters StatPara = new StatisticsParameters();

}
