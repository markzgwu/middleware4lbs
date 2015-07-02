package measure.LocKBA.model;

import org.apache.commons.math3.stat.Frequency;

public final class SnopshotStatistics {
	String[] cellidlist;
	public void mount(String[] cellids){
		this.cellidlist = cellids;
	}
	
	public Frequency getFrequency(){
		Frequency freq = new Frequency();
		for(String cellid:cellidlist){
			freq.addValue(cellid);
		}
		return freq;
	}
	
	
}
