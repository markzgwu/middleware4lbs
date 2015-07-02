package measure.LocKBA.model;

import org.apache.commons.math3.stat.Frequency;
import org.tools.forLog.ILog;

public final class ObjectAtTag implements ILog{
	final String samplecellid;
	final String targetcellid;
	final Frequency freq;
	//final String[] cellids;
	public ObjectAtTag(String samplecellid, String targetcellid,
			//String[] cellids) {
			final Frequency freq){
		super();
		this.samplecellid = samplecellid;
		this.targetcellid = targetcellid;
		//this.cellids = cellids;
		this.freq = freq;
	}
	
}
