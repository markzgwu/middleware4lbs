package measure.AuthKBA.experiments;

public final class ParameterPackage {
	int claimant = 1;
	
	double success_request = ParametersDb.success_request;
	double total_request= ParametersDb.total_request;

	
	int start_column= 2;
	int profile_len = 3;//0表示不选择porfile作为数据源
	int movies_len = 20;
	double memory_ability = 0.9;
	
	public ParameterPackage(double success_r,double total_r,int claimant_input){
		success_request = success_r;
		total_request= total_r;
		claimant=claimant_input;
	}
	
	public ParameterPackage(){
	}
	
	public ParameterPackage(int claimant_input){
		claimant=claimant_input;
	}
	
	public double get_parameter_reliability_evdience(int knowledge_source_index){
		double reliability = 1;
		//reliability = get_parameter_memory_ability(knowledge_source_index);
		reliability = memory_ability;
		return reliability;
	}	
	
	public double get_parameter_memory_ability(int knowledge_source_index){
		double reliability = 1;
		if(knowledge_source_index<profile_len){
			reliability=0.99;
		}else{
			reliability=0.9;
		}
		return reliability;
	}	
}
