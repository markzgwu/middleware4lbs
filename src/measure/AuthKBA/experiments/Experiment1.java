package measure.AuthKBA.experiments;

public final class Experiment1 {
	static int round = 10;
	
	public static void experiment_attacker5(){
		String s = "";
		for(int i=1;i<2;i++){
			s+=experiment_attacker5(i)+",";
		}
		System.out.println(s);
	}
	
	public static double experiment_attacker5(int index_threshole){
		// TODO Auto-generated method stub
		ParameterPackage parameters= new ParameterPackage();
		
		parameters.total_request=100*index_threshole+1;
		parameters.movies_len = 10;
		
		System.out.println("\nexperiment_attacker1 "+parameters.total_request);
		
		double Count_for_FAR = 0; 
		double number_users = 0;
		for(int userid=1;userid<100;userid++){
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
				number_users++;
				
		}
		
		double FAR = Count_for_FAR/number_users;
		System.out.println("FAR:"+FAR);
		System.out.println();
		return FAR;
		
	}	
	
	public static void experiment_attacker1(){
		String s = "";
		for(int i=1;i<20;i++){
			s+=experiment_attacker1(i)+",";
		}
		System.out.println(s);
	}
	

	public static double experiment_attacker1(int index_threshole){
		
		// TODO Auto-generated method stub
		ParameterPackage parameters= new ParameterPackage();
		
		parameters.total_request=100*index_threshole+1;
		parameters.movies_len = 10;
		
		System.out.println("\nexperiment_attacker1 "+parameters.total_request);
		
		double SUM_FAR=0;
		for(int userid=1;userid<round;userid++){
			parameters.claimant = userid;
			DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
			DataPackage mydp = readytask.GetDataPackage();
			
			String[][] knownledge_data = mydp.getInput_knownledge_data();
			
			Measurement m = new Measurement(mydp.getInput_knownledge_data(),parameters);
			m.print_entropys();
			System.out.println();	
			double Count_for_FAR = 0; 
			double number_users = 0;
			for(int attacker=1;attacker<knownledge_data.length;attacker++){
				if(userid!=attacker){
					mydp.setInput_evdience(knownledge_data[attacker]);
				}
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
				number_users++;
			}

			double FAR = Count_for_FAR/number_users;
			System.out.println("FAR:"+FAR);
			System.out.println();
			SUM_FAR+=FAR;
		}
		
		double average_far = (SUM_FAR/round);
		System.out.println("AVERAGE_FAR:"+average_far);
		System.out.println();
		return average_far;
	
	}	
	
	////////////////////
	public static void experiment_attacker(){
		String s = "";
		for(int i=1;i<21;i++){
			s+=experiment_attacker(i)+",";
		}
		System.out.println(s);
	}
	
	public static double experiment_attacker(int movies_len){
		System.out.println("\nexperiment_attacker");
		// TODO Auto-generated method stub
		ParameterPackage parameters= new ParameterPackage();
		//parameters.total_request=10*round;
		parameters.movies_len = movies_len;
		double SUM_FAR=0;
		for(int userid=1;userid<round;userid++){
			parameters.claimant = userid;
			
			DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
			DataPackage mydp = readytask.GetDataPackage();
			
			String[][] knownledge_data = mydp.getInput_knownledge_data();
			
			Measurement m = new Measurement(mydp.getInput_knownledge_data(),parameters);
			m.print_entropys();
			System.out.println();	
			double Count_for_FAR = 0; 
			double number_users = 0;
			for(int attacker=1;attacker<knownledge_data.length;attacker++){
				if(userid!=attacker){
					mydp.setInput_evdience(knownledge_data[attacker]);
				}
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
				number_users++;
			}

			double FAR = Count_for_FAR/number_users;
			System.out.println("FAR:"+FAR);
			System.out.println();
			SUM_FAR+=FAR;
		}
		
		double average_far = (SUM_FAR/round);
		System.out.println("AVERAGE_FAR:"+average_far);
		System.out.println();
		return average_far;
	
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
	
	public static void experiment_4_old(){
		System.out.println("\nexperiment_4");
		ParameterPackage parameters= new ParameterPackage();
		parameters.profile_len = 2;
		String message1 = "";
		for(int test_movies_len=0;test_movies_len<21;test_movies_len++){
			parameters.movies_len = test_movies_len;
			
			double Count_for_FRR = 0; 
			// TODO Auto-generated method stub
			double number_users = 0;

			for(int i=1;i<50;i++){
				parameters.claimant = 1;
				DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
				DataPackage mydp = readytask.GetDataPackage();
				
				NBStringV2 NBtask = new NBStringV2(mydp);
				
				parameters.total_request=i;
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
			
			message1+=FRR+",";
			
		}
		
		System.out.println(message1);	
		
	}	
	
	public static void experiment_2_old(){
		System.out.println("\nexperiment_2");
		ParameterPackage parameters= new ParameterPackage();
		
		double Count_for_FRR = 0; 
		// TODO Auto-generated method stub
		double number_users = 0;

		for(int i=1;i<50;i++){
			
			parameters.claimant = 1;
			
			DoExtractDataSlimProfile readytask = new DoExtractDataSlimProfile(parameters);
			DataPackage mydp = readytask.GetDataPackage();
			
			NBStringV2 NBtask = new NBStringV2(mydp);
			
			parameters.total_request=i;
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
		for(int i=1;i<31;i++){
			
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
		
		//experiment_4();
		//experiment_attacker();
		//experiment_1();
		//experiment_2();
		//experiment_attacker1();
		experiment_attacker5();
		//experiment_3();
		
		long time=System.currentTimeMillis()-start;
		System.out.println("TEST TIME:"+time/1000);

	}

}
