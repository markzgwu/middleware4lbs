package me.projects.LISG.experiments;

import org.tools.forMatlab.Format4Matlab;

import me.projects.LISG.methods.*;

public final class Experiment2 {

	double oneInstance(int level,double constant_Pr_A){
		DataTrainer2 dataTrainer = new DataTrainer2(level);
		dataTrainer.Constant_Pr_A = constant_Pr_A;
		LocationSemanticModel varLSM = new LocationSemanticModel(dataTrainer);
		//varLSM.test1();
		double EX = 0;
		for(String locTag:varLSM.dataTrainer.LocationTags){
			//varLSM.test2();
			EX += varLSM.PosteriorBelief_Pr_A_Oloc(locTag);
		}
		EX = EX/varLSM.dataTrainer.LocationTags.size();
		//System.out.println(EX);
		return EX;
	}
	
	void worker(){
		int[] levels = new int[]{4,5,6};
		String output = "";
		int label = 0;
		for(int level:levels){
			
			String result = "";
			for(int i=1;i<=20;i++){
				double constant_Pr_A = i*0.05;
				double average_risk = oneInstance(level,constant_Pr_A);
				result+=average_risk+",";
			}
			//System.out.println(result);
			result = Format4Matlab.output4matlab("L"+label, result);
			output += result+"\n";
			
			label++;
		}
		System.out.println(output);
	}
	
	public static void main(String[] args) {
		new Experiment2().worker();
	}

}
