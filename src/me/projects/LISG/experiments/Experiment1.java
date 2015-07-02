package me.projects.LISG.experiments;

import org.parameters.I_constant;
import org.tools.forMatlab.Format4Matlab;

import me.projects.LISG.methods.*;

public final class Experiment1 {

	String oneInstance(int level,double constant_Pr_A){
		DataTrainer2 dataTrainer = new DataTrainer2(level);
		dataTrainer.Constant_Pr_A = constant_Pr_A;
		LocationSemanticModel varLSM = new LocationSemanticModel(dataTrainer);
		//varLSM.test1();
		String result = "";
		for(String locTag:varLSM.dataTrainer.LocationTags){
			//varLSM.test2();
			result += varLSM.PosteriorBelief_Pr_A_Oloc(locTag)+",";
		}
		//System.out.println(EX);
		return result;
	}
	
	void worker(){
		String output = "";
		int label = 0;
		String result = "";
		double constant_Pr_A = 0.05;
		int level = 4;
		result = oneInstance(level,constant_Pr_A);
		//System.out.println(result);
		result = Format4Matlab.output4matlab("L"+label, result);
		output += result+"\n";

		System.out.println(output);
	}
	
	public static void main(String[] args) {
		//new Experiment1().worker();
		System.out.println(I_constant.output_detail);
		System.out.println(I_constant.output_detail.equals("true"));
	}

}
