package org.experiments.comparison.warper;

import java.util.ArrayList;

import org.tools.forMatlab.Format4Matlab;

public final class Dataoutput {
	public String[] argumentarray;
	public String[] outputarray;
	public void init(){
		argumentarray = new String[]{
				"demand_","dl_","ratio_",
				"time_",
				"average_",
				};
		outputarray = new String[argumentarray.length];
		for(int i=0;i<outputarray.length;i++){
			outputarray[i] = "";
		}		
	}
	
	public void output(String prefix,String name){
		for(int i=0;i<argumentarray.length;i++){
			final String output = prefix+Format4Matlab.output4matlab(argumentarray[i]+name, outputarray[i]);
			System.out.println(output);
		}
	}	
}
