package me.projects.LISG.experiments;

import org.tools.forMatlab.Format4Matlab;

import me.projects.LISG.methods.*;

public final class Experiment3V2 {

	
	String oneInstance(){
		
		String result = "";
		for(String locTag:varLSM.dataTrainer.LocationTags){
			//varLSM.test2();
			result += varLSM.PosteriorBelief_Pr_A_Oloc(locTag)+",";
		}
		//System.out.println(EX);
		return result;
	}
	
	DataSampler datasampler = null;
	AbsDataTrainer dataTrainer = null;
	LocationSemanticModel varLSM = null;
	void worker(){
		Parameters para = new Parameters(4);
		ParameterTable.INSTANCE.init(para);
		datasampler = ParameterTable.INSTANCE.getDatasampler();
		dataTrainer = new DataTrainer5(datasampler);			
		varLSM = new LocationSemanticModel(dataTrainer);
		//varLSM.test1();
		String output = "";
		int label = 0;
		String result = "";
		result = oneInstance();
		//System.out.println(result);
		result = Format4Matlab.output4matlab("L"+label, result);
		output += result+"\n";

		System.out.println(output);
	}
	
	
	public static void main(String[] args) {
	
		new Experiment3V2().worker();
	}

}
