package me.projects.toolkit;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.Frequency;

public final class ToolkitFrequency {
	
	public static final double getEntropy(final Frequency P){
		double entropy = 0;
		/*
		final Iterator<Entry<Comparable<?>, Long>> m = P.entrySetIterator();
		while(m.hasNext()){
			String cellid=(String)m.next().getKey();
			double px = P.getPct(cellid);
			double var1 = (-1)* px * Math.log(px);
			entropy += var1 ;
		}
		*/
	    final HashSet<String> P_KeySet = getKeySet(P);
		for(String cellid:P_KeySet){
			double px = P.getPct(cellid);
			double var1 = (-1)* px * Math.log(px);
			entropy += var1 ;			
		}
		return entropy;
	}
	
	public static final HashSet<String> getKeySet(final Frequency P){
		final HashSet<String> P_keyset = new HashSet<String>();
		final Iterator<Entry<Comparable<?>, Long>> iter = P.entrySetIterator();
		while(iter.hasNext()){
			final String key=(String)iter.next().getKey();
			P_keyset.add(key);
		}
		return P_keyset;
	}
	
	public static final double getRelativeEntropy(final Frequency P,final Frequency Q){
		HashSet<String> P_Q_keyset = getKeySet(P);
		P_Q_keyset.addAll(getKeySet(Q));
		double relativeEntropy = 0;
		for(String cellid:P_Q_keyset){
			double P_x = P.getPct(cellid);
			double Q_x = Q.getPct(cellid);
			if((P_x > 0)&&(Q_x>0)){
				double var1 = P_x * Math.log(P_x/Q_x);
				relativeEntropy += var1 ;
			}
			
		}		
		return relativeEntropy;
	}
	
}
