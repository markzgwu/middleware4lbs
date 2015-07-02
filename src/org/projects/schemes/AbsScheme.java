package org.projects.schemes;

import java.util.HashSet;

import me.projects.LISG.methods.LocationSemanticModel;
import me.projects.QP.methods.QPEventStat;
import me.projects.QP.methods.prediction.CRPrediction;
import me.projects.QP.methods.prediction.TargetReguestRecorder;

import org.apache.commons.math3.stat.Frequency;
import org.parameters.I_constant;
import org.projects.caches.ICache;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.MovingObject;
import org.projects.providers.IDBProvider;
import org.tools.forLog.ILog;

public abstract class AbsScheme implements ILog{
	/*
	public AbsScheme(final String[] dataset){
		this.dataset = dataset;
	}
	*/
	
	public AbsScheme(){
		
	}
	
	public String[] dataset = null;
	public void mount(final String[] dataset){
		this.dataset = dataset;		
	}
	protected String data;
	
	public int demandcells = 0;
	public int downloadcells = 0;
	public int cloakingfailure = 0;
	public MovingObject currentMovingObject;
	public QPEventStat QPSTAT = new QPEventStat();
	public double risk = 0;
	final public int subpath_length = I_constant.subpath_length;
	final boolean b_output_detail = I_constant.output_detail.equals("true");
	//public ArrayList<String> cellids = null;
	public long elapsedTime;
	public long elapsedTime1;
	public long elapsedTime2;
	public void clear(){
		dataset = null;
		//cellids = null;
		demandcells=0;
		downloadcells=0;
		cloakingfailure = 0;
		QPSTAT = new QPEventStat();
		elapsedTime = 0;
		elapsedTime1 = 0;
		elapsedTime2 = 0;
		
	}
	
	String currentcell = null;
	public String getCurrentcell(){
		return currentcell;
	}
	String[] cloakingregion = null;
	public String[] getCloakingregion(){
		return cloakingregion;
	}
	
	
	public int getNumCell(){
		final int level = this.rectenv.getLevel().level;
		int NumCell = 0;
		for(String cell:cloakingregion){
			final int sublevel = level - cell.length();
			NumCell+=Math.pow(4, sublevel);
		}
		return NumCell;
	}
	
	public abstract void download();
	
	protected RectEnv rectenv = null;
	public void init(final RectEnv rectenv){
		this.rectenv = rectenv;
	}
	
	protected ICache cachezone = null;
	public void mount(final ICache cachezone){
		this.cachezone = cachezone;
	}
	
	protected LocationSemanticModel varLSM  = null;
	public void mount(final LocationSemanticModel varLSM){
		this.varLSM = varLSM;
	}
	
	protected IDBProvider dbprovider = null;
	public void mount(final IDBProvider dbprovider){
		this.dbprovider = dbprovider;
	}
	
	protected Frequency frequency = null;
	public void mount(final Frequency frequency){
		this.frequency = frequency; 
	}
	public final Frequency getFrequency() {
		return frequency;
	}
	
	public CRPrediction CRChecker = null;
	public void mount(final CRPrediction CRChecker){
		this.CRChecker = CRChecker; 
	}
	
	public TargetReguestRecorder TarReqRec = null;
	public void mount(final TargetReguestRecorder oneTarReqRec){
		this.TarReqRec = oneTarReqRec; 
	}

	public HashSet<String> TargetObjectID = null;
	
	public void setup(final HashSet<String> oneTargetObjectID){
		this.TargetObjectID = oneTargetObjectID; 
	}
	
}
