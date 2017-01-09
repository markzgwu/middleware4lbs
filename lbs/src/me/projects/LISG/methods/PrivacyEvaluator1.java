package me.projects.LISG.methods;

import org.apache.commons.math3.stat.Frequency;

public final class PrivacyEvaluator1 {

	void test1(){
		double Pr_A_Oloc = 0;
		double Pr_Oloc_A = 0.5*1+0.5*0+0*0;
		double Pr_A = 0.4;
		double Pr_Oloc = 0.3;
		Pr_A_Oloc = (Pr_Oloc_A * Pr_A) / Pr_Oloc;
		double VarPosteriorBelief = Pr_A_Oloc;
		System.out.println("PosteriorBelief:P(A=1|Oloc=L1)="+VarPosteriorBelief);
	}
	
	public static void main(String[] args) {
		final DataSampler ds = ParameterTable.INSTANCE.getDatasampler();
		final Frequency freq = ds.getFreq();
		System.out.println(freq);
		
		LocationSemanticModel varLSM = new LocationSemanticModel(new DataTrainer1());
		//varLSM.test1();
		double EX = 0;
		for(String locTag:varLSM.dataTrainer.LocationTags){
			//varLSM.test2();
			EX += varLSM.PosteriorBelief_Pr_A_Oloc(locTag);
		}
		EX = EX/varLSM.dataTrainer.LocationTags.size();
		System.out.println("Expectation(Average Risk):"+EX);
		
	}

}
