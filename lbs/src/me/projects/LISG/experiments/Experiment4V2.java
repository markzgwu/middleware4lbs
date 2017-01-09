package me.projects.LISG.experiments;

import org.tools.forMatlab.Format4Matlab;

import me.projects.LISG.methods.*;

public final class Experiment4V2 {
	int label = 0;
	String onestep(int level){
		//varLSM.test1();
		
		String result = "";
		
		Parameters para = new Parameters(level);
		ParameterTable.INSTANCE.init(para);
		final MapsReaderV2 mapsreader = new MapsReaderV2();
		for(String catalog:mapsreader.getSemanticLabels()){
			double freq = mapsreader.freq_catalog.getPct(catalog);
			result+=freq+",";
		}

		//System.out.println(result);
		result = Format4Matlab.output4matlab("L"+label, result);
		return result;
	}
	
	void worker(){
		String output = "";
		String result = "";
		int[] levels = {4,5,6};
		for(int level:levels){
			
			result = onestep(level);
			output += result+"\n";
			label++;
			
		}
		
		System.out.println(output);
	}
	
	public static void main(String[] args) {
	
		new Experiment4V2().worker();
		

		
	}

}
