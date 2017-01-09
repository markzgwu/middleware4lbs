package me.projects.QP.methods.prediction;

import me.projects.LISG.methods.DataSampler;

import org.tools.forLog.ILog;
import org.tools.forMaths.MathTool4BigDec;

public abstract class AbsCRPrediction implements ILog{
	String title = null;
	final StringBuffer result = new StringBuffer();
	
	final DataSampler datasampler;
	public AbsCRPrediction(final DataSampler datasampler){
		this.datasampler = datasampler;
		setup();
	}
	
	abstract void setup();
	protected final double round(double value){
		return MathTool4BigDec.Round(value);
	}
}
