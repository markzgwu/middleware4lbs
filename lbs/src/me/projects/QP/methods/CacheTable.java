package me.projects.QP.methods;

import java.util.HashMap;
import java.util.HashSet;

public enum CacheTable {
	INSTANCE;
	CacheTable(){

	}
	
	public final HashMap<String,Double> CachePostBeliefs = new HashMap<String,Double>();
	public final HashSet<String> ZeroPostBeliefsInSet  =   new HashSet<String>();
	public final SimpleBloomFilter ZeroPostBeliefs  =   new SimpleBloomFilter();
	
}