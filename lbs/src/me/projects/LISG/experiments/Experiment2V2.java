package me.projects.LISG.experiments;

import org.tools.forMatlab.Format4Matlab;

import me.projects.LISG.methods.*;

public final class Experiment2V2 {

	double oneInstance(){
		//varLSM.test1();
		double p = 0;
		for(String locTag:varLSM.dataTrainer.LocationTags){
			//varLSM.test2();
			double a = varLSM.PosteriorBelief_Pr_A_Oloc(locTag);
			//System.out.println("varLSM.PosteriorBelief_Pr_A_Oloc="+a);
			p += a;
		}
		int b = varLSM.dataTrainer.LocationTags.size();
		double EX = p/b;
		System.out.println("EX("+EX+")=P("+p+")/b("+b+")");
		return EX;
	}
	
	DataSampler datasampler = null;
	AbsDataTrainer dataTrainer = null;
	LocationSemanticModel varLSM = null;
	
	void worker(){
		final int[] levels = new int[]{4,5,6,7};
		String output = "";
		int label = 0;
		for(int tmplevel:levels){			
			String result = "";
			for(int i=0;i<=20;i++){
				double constant_Pr_A = i*0.05;
				ParameterTable.INSTANCE.init(new Parameters(tmplevel,constant_Pr_A));
				datasampler = ParameterTable.INSTANCE.getDatasampler();
				dataTrainer = new DataTrainer5(datasampler);
				varLSM = new LocationSemanticModel(dataTrainer);
				double average_risk = oneInstance();
				result+=average_risk+",";
			}
			//System.out.println(result);
			result = Format4Matlab.output4matlab("L"+label, result);
			output += result+"\n";
			
			label++;
		}
		System.out.println(output);
	}
	
	public static void main(String[] args){
		new Experiment2V2().worker();
	}

}
