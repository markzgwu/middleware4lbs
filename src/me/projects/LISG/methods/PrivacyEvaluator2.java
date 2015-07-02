package me.projects.LISG.methods;

public final class PrivacyEvaluator2 {

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

		LocationSemanticModel varLSM = new LocationSemanticModel(new DataTrainer2(4));
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
