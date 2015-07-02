package measure.AuthKBA.experiments;

public final class Experiment2 {
	static int round = 10;
	
	public static void experiment_attacker5(){
		String far = "";
		String frr = "";
		for(int i=1;i<11;i++){
			double[] far_frr = experiment_attacker5(i);
			far+=far_frr[0]+",";
			frr+=far_frr[1]+",";
		}
		System.out.println("FAR:"+far);
		System.out.println("FRR:"+frr);
	}
	
	public static double[] experiment_attacker5(int threshole){
		// TODO Auto-generated method stub
		double[] far_frr=new double[2];
		ParameterPackage parameters= new ParameterPackage();
		
		//parameters.total_request=100*index_threshole+1;
		parameters.profile_len = 0;
		parameters.total_request = threshole+1;
		parameters.movies_len = 15;
		
		System.out.println("\nexperiment_attacker5 "+parameters.total_request);
		
		double Count_for_FAR = 0; 
		double Count_for_FRR = 0;
		double number_users = 0;
		for(int userid=1;userid<100;userid++){
			number_users++;
			
			
			parameters.claimant=userid;
			DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
			DataPackage mydp = readytask.GetDataPackage();
			
			String[][] knownledge_data = mydp.getInput_knownledge_data();
			
			Measurement m = new Measurement(mydp.getInput_knownledge_data(),parameters);
			AttackerGenerator ag = new AttackerGenerator(knownledge_data,parameters);
			m.print_entropys();
			System.out.println();	
			
			mydp.setInput_evdience(ag.attacker1());
			NBStringV2 NBtask = new NBStringV2(mydp);
			NBtask.input_Parameters(parameters);
			String message = ""+NBtask.pr_prior_true_for_evdience();
			message+=";"+(NBtask.pr_prior_false_for_evdience());
			message+=";"+(NBtask.likelihood_ratio_for_evdience());
			message+=";"+(NBtask.threshold());
			message+=";"+(NBtask.decisionmaking());
			if(NBtask.decisionmaking()){
				System.out.println(message);
			}
			
			if(NBtask.decisionmaking()){
				Count_for_FAR++;
			}
			
			////////////////////////////////////////////////////
			mydp.setInput_evdience(knownledge_data[userid]);
			NBStringV2 NBtask1 = new NBStringV2(mydp);
			NBtask1.input_Parameters(parameters);
			String message1 = ""+NBtask1.pr_prior_true_for_evdience();
			message+=";"+(NBtask1.pr_prior_false_for_evdience());
			message+=";"+(NBtask1.likelihood_ratio_for_evdience());
			message+=";"+(NBtask1.threshold());
			message+=";"+(NBtask1.decisionmaking());
			if(!NBtask1.decisionmaking()){
				System.out.println(message1);
			}
			
			if(!NBtask1.decisionmaking()){
				Count_for_FRR++;
			}			
			
			
				
		}
		
		double FAR = Count_for_FAR/number_users;
		double FRR = Count_for_FRR/number_users;
		far_frr[0] = FAR;
		far_frr[1] = FRR;
		System.out.println("FAR:"+FAR+";FRR:"+FRR);
		System.out.println();
		
		return far_frr;
		
	}		
	
	public static void experiment_1(){
		System.out.println("\nexperiment_1");
		ParameterPackage parameters= new ParameterPackage();
		
		double Count_for_FRR = 0; 
		// TODO Auto-generated method stub
		double number_users = 0;
		for(int userid=1;userid<round;userid++){
			parameters.claimant = userid;
			
			DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
			DataPackage mydp = readytask.GetDataPackage();
			
			ParameterPackage mypp=new ParameterPackage();
			Measurement m = new Measurement(mydp.getInput_knownledge_data(),mypp);
			m.print_entropys();
			System.out.println();			
			
			NBStringV2 NBtask = new NBStringV2(mydp);

			NBtask.input_Parameters(parameters);			
			
			String message = ""+NBtask.pr_prior_true_for_evdience();
			message+=";"+(NBtask.pr_prior_false_for_evdience());
			message+=";"+(NBtask.likelihood_ratio_for_evdience());
			message+=";"+(NBtask.threshold());
			message+=";"+(NBtask.decisionmaking());
			System.out.println(message);
			if(!NBtask.decisionmaking()){
				Count_for_FRR++;
			}
			System.out.println();
			number_users++;
		}
		double FRR = Count_for_FRR/number_users;
		System.out.println("FRR:"+FRR);
	}
	
	public static void experiment_3(){
		System.out.println("\nexperiment_3");
		String time_metric="";
		long start=System.currentTimeMillis();
		
		ParameterPackage parameters= new ParameterPackage();
		
		double Count_for_FRR = 0; 
		// TODO Auto-generated method stub
		double number_users = 0;
		for(int i=1;i<20;i++){
			
			parameters.movies_len = i;
			
			parameters.claimant = 1;
			DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
			
			DataPackage mydp = readytask.GetDataPackage();
			
			NBStringV2 NBtask = new NBStringV2(mydp);
			NBtask.input_Parameters(parameters);
			
			String message = ""+NBtask.pr_prior_true_for_evdience();
			message+=";"+(NBtask.pr_prior_false_for_evdience());
			message+=";"+(NBtask.likelihood_ratio_for_evdience());
			message+=";"+(NBtask.threshold());
			message+=";"+(NBtask.decisionmaking());
			System.out.println(message);
			
			if(!NBtask.decisionmaking()){
				Count_for_FRR++;
			}
			
			System.out.println();
			number_users++;
			
			long time=System.currentTimeMillis()-start;
			double ms_s = 1000;
			time_metric+=(time/ms_s)+",";
		}
		double FRR = Count_for_FRR/number_users;
		System.out.println("FRR:"+FRR);
		System.out.println(time_metric);
	}
	
	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		
		experiment_attacker5();
		
		long time=System.currentTimeMillis()-start;
		System.out.println("TEST TIME:"+time/1000);

	}

}
