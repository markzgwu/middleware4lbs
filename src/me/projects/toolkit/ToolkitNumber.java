package me.projects.toolkit;

import java.util.ArrayList;

public final class ToolkitNumber {
	final static public String show(final double[] a){
		String r = "";
		for(double num:a){
			r+=num+";";
		}
		return r;
	}
	
	final static public double[] convert(final ArrayList<Double> a){
		double[] b = new double[a.size()];
		for(int i=0;i<a.size();i++){
			b[i] = a.get(i);
		}
		return b;
	}	
}
