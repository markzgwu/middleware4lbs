package org.projects.metrics;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.math3.stat.Frequency;
import org.projects.datamodel.RectEnv;
import org.projects.preprocessing.Convertor;

public class Statistics implements IStatistics{
	Frequency frequency = null;
	@Override
	public void mount(final Frequency frequency) {
		this.frequency = frequency;
	}
	
	RectEnv rectenv = null;
	public void mount(final RectEnv rectenv){
		this.rectenv = rectenv;
	}
	
	public void output(){
		Iterator<?> i = frequency.entrySetIterator();
		while(i.hasNext()){
			String a = i.next().toString();
			System.out.println(a);
		}
		
	}	
	
	public void output(String prefix_nodeid){
		final ArrayList<String> cellids= Convertor.extend(prefix_nodeid,rectenv.getLevel().level);
		for(String cellid:cellids){
			System.out.println(cellid+":"+frequency.getPct(cellid));
		}
	}
	
}
