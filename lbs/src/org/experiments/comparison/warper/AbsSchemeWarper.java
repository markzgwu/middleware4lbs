package org.experiments.comparison.warper;

import org.experiments.comparison.warper.Dataoutput;
import org.projects.caches.ICache;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.LoadingMO;
import org.projects.providers.IDBProvider;
import org.projects.schemes.AbsScheme;

public abstract class AbsSchemeWarper {
	final protected AbsScheme scheme;
	public AbsSchemeWarper(final AbsScheme scheme){
		this.scheme = scheme;
	}
	
	protected RectEnv rectenv = null;
	protected LoadingMO data = null;
	protected IDBProvider dbsp = null;
	protected ICache bufferzone = null;
	public void mount(final RectEnv rectenv,
			final LoadingMO data,
			final IDBProvider dbsp,
			final ICache bufferzone){
		this.rectenv = rectenv;
		this.data = data;
		this.dbsp = dbsp;
		this.bufferzone = bufferzone;
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

}
