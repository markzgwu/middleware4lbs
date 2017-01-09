package measure.AuthKBA.experiments;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

public final class NBStringV2 extends AbstractData{
	final static public boolean log = false;
	
	double likenessrate=0;
	
	StringBuffer message = new StringBuffer();
	
	ParameterPackage parameters = null;
	
	public void input_Parameters(ParameterPackage input_parameters){
		parameters = input_parameters;
	}
	
	String[] evdience=null;	
	int claimant_index_in_data=0;
	public NBStringV2(int Input_claimant_index_in_data,String[] Input_evdience,String[][] Input_knownledge_data){
		knownledge_data = Input_knownledge_data;
		evdience = Input_evdience;
		claimant_index_in_data=Input_claimant_index_in_data;
	}
	
	public NBStringV2(DataPackage testdata){
		knownledge_data = testdata.getInput_knownledge_data();
		evdience = testdata.getInput_evdience();
		claimant_index_in_data=testdata.getInput_claimant_index_in_data();
	}	
	
	public static void main(String[] args) {
		String[][] sample_knownledge_data = {{"a","b"},{"a","b"},{"a","b"},{"0","b"},{"0","b"},{"0","b"},{"0","b"},{"0","b"},{"0","0"},{"0","0"}};
		String[] sample_evdience={"a","b"};
		String[] sample_evdience_imposter1={"a","0"};
		String[] sample_evdience_imposter2={"0","0"};
		
		ParameterPackage parameters= new ParameterPackage();
		Measurement m = new Measurement(sample_knownledge_data,parameters);
		m.print_entropys();
		System.out.println();
		
		NBStringV2 task0 = new NBStringV2(0,sample_evdience,sample_knownledge_data);
		task0.input_Parameters(parameters);
		
		System.out.println(task0.pr_prior_true_for_evdience());
		System.out.println(task0.pr_prior_false_for_evdience());
		System.out.println(task0.likelihood_ratio_for_evdience());
		System.out.println(task0.threshold());
		System.out.println(task0.decisionmaking());
		System.out.println();
		
		NBStringV2 task1 = new NBStringV2(0,sample_evdience_imposter1,sample_knownledge_data);
		task1.input_Parameters(parameters);
		
		System.out.println(task1.pr_prior_true_for_evdience());
		System.out.println(task1.pr_prior_false_for_evdience());
		System.out.println(task1.likelihood_ratio_for_evdience());
		System.out.println(task1.threshold());
		System.out.println(task1.decisionmaking());
		System.out.println();
		
		NBStringV2 task2 = new NBStringV2(0,sample_evdience_imposter2,sample_knownledge_data);
		task2.input_Parameters(parameters);
		
		System.out.println(task2.pr_prior_true_for_evdience());
		System.out.println(task2.pr_prior_false_for_evdience());
		System.out.println(task2.likelihood_ratio_for_evdience());
		System.out.println(task2.threshold());
		System.out.println(task2.decisionmaking());
		System.out.println();
		
		AttackerGenerator ag = new AttackerGenerator(sample_knownledge_data, parameters);
		String[] sample_evdience_imposter3=ag.attacker1();
		
		NBStringV2 task3 = new NBStringV2(0,sample_evdience_imposter3,sample_knownledge_data);
		task3.input_Parameters(parameters);
		
		System.out.println(task3.pr_prior_true_for_evdience());
		System.out.println(task3.pr_prior_false_for_evdience());
		System.out.println(task3.likelihood_ratio_for_evdience());
		System.out.println(task3.threshold());
		System.out.println(task3.decisionmaking());
		System.out.println();		

	}
	
	public double pr_prior_true(){
		double success_request = parameters.success_request;
		double total_request = parameters.total_request;
		
		double parameter_pr_prior_true = success_request/total_request;
		return parameter_pr_prior_true;
	}
	
	public double threshold(){
		double parameter_pr_prior_true = pr_prior_true();
		double threshold = (1-parameter_pr_prior_true)/parameter_pr_prior_true;
		return threshold;
	}
	
	public boolean decisionmaking(){
		boolean decisionmaking = (this.likelihood_ratio_for_evdience()>this.threshold());
		return decisionmaking;
	}
	

	public double likelihood_ratio_for_evdience(){
		double pr_prior_true_for_evdience = this.pr_prior_true_for_evdience();
		double pr_prior_false_for_evdience=this.pr_prior_false_for_evdience();	
		double likelihood_ratio_for_evdience = 0;		
		likelihood_ratio_for_evdience = pr_prior_true_for_evdience/pr_prior_false_for_evdience;
		return likelihood_ratio_for_evdience;
	}
	
	public double pr_prior_true_for_evdience_2(){
		String[] evdience_claimant=this.evdience;
		String[] evdience_in_data=this.knownledge_data[this.claimant_index_in_data];
		//double pr_prior_true_for_evdience = 1;
		int n = evdience_claimant.length;
		double reliability_evdience = 0;
		for(int j=0;j<evdience_claimant.length;j++ ){
			boolean b = StringUtils.equals(evdience_claimant[j], evdience_in_data[j]);
			if(b){
				reliability_evdience++;
			}
		}
		double pr_prior_true_for_evdience = reliability_evdience/n;
		return pr_prior_true_for_evdience;
	}	
	
	public double pr_prior_true_for_evdience(){
		double pr_prior_true_for_evdience = 1;
		String[] evdience_claimant=this.evdience;
		String[] evdience_in_data=this.knownledge_data[this.claimant_index_in_data];

		for(int j=0;j<evdience_claimant.length;j++ ){
			boolean b = StringUtils.equals(evdience_claimant[j], evdience_in_data[j]);
			double reliability_evdience = parameters.get_parameter_reliability_evdience(j);
			if(b){
				pr_prior_true_for_evdience*=reliability_evdience;
			}else{
				pr_prior_true_for_evdience*=(1-reliability_evdience);
			}
		}
		return pr_prior_true_for_evdience;
	}	
	
	public double pr_prior_false_for_evdience(){	
		double pr_prior_false_for_evdience=1;
		String[] evdience_claimant=this.evdience;
		String[] evdience_in_data=this.knownledge_data[this.claimant_index_in_data];

		for(int j=0;j<evdience_claimant.length;j++ ){		
			boolean b = StringUtils.equals(evdience_claimant[j], evdience_in_data[j]);
			double g_if_right_answer = 0;
			
			if(ParametersDb.IID_assumption){
				g_if_right_answer = g(j);
			}else{
				g_if_right_answer = g_generic_sup(j,b);
			}
			
			
			if(b){
				pr_prior_false_for_evdience*=g_if_right_answer;
			}else{
				pr_prior_false_for_evdience*=(1-g_if_right_answer);
			}			
			
		}
		return pr_prior_false_for_evdience;
	}	
	
	public double pr_prior_false_for_evdience_wrong(){	
		double pr_prior_false_for_evdience=1;
		for(int j=0;j<evdience.length;j++){
			pr_prior_false_for_evdience*=g(j);
		}
		
		return pr_prior_false_for_evdience;
	}	
	
	public double pr_prior_false_for_evdience_2(){	
		double pr_prior_false_for_evdience=1;
		for(int j=0;j<evdience.length;j++){
			pr_prior_false_for_evdience*=pr_prior_false_for_evdience(j);
		}
		
		return pr_prior_false_for_evdience;
	}
	
	double pr_prior_false_for_evdience(int column_index){
		HashMap<String,Integer> values_column = count_for_column(column_index);
		double a = 1;
		double pr_prior_false_for_evdience_column=a/values_column.size();
		return pr_prior_false_for_evdience_column;
	}		
	
	double pr_prior_false_for_evdience1(int column_index){
		double pr_prior_false_for_evdience_column=count_for_frequency_evdience(column_index)/this.knownledge_data.length;
		return pr_prior_false_for_evdience_column;
	}
	
	private double count_for_frequency_evdience(int column_index){
		int rows_number = this.knownledge_data.length;
		double count=0;
		for(int i=0;i<rows_number;i++){
			boolean b = StringUtils.equals(this.evdience[column_index],this.knownledge_data[i][column_index]);
			if(b){
				count++;
			}
		}
		return count;
	}
	
}
