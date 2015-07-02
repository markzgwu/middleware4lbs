package measure.AuthKBA.experiments;

import java.util.ArrayList;
import java.util.HashMap;

public class Measurement extends CountData{
	
	public Measurement(String[][] input_dataset, ParameterPackage mypp) {
		super(input_dataset, mypp);
		// TODO Auto-generated constructor stub
	}

	public void print_entropys(){
		ArrayList<Double> entropys = entropys();
		int index = 0;
		double total_entropy = 0;
		double total_entropy_profile = 0;
		double total_entropy_movies = 0;
		for(double entropy:entropys){
			//System.out.println("i:"+index+";"+entropy);
			if(index<MyParameterPackage.profile_len){
				total_entropy_profile+=entropy;
			}else{
				total_entropy_movies+=entropy;
			}
			total_entropy+=entropy;
			index++;
		}
		
		System.out.println("ENTROPY-> profile:"+total_entropy_profile+";movies:"+total_entropy_movies+";TOTAL:"+total_entropy);
		
	}
	
	public ArrayList<Double> entropys(){
		ArrayList<Double> entropys_column = new ArrayList<Double>();
		int num_column = knownledge_data[0].length;
		for(int j=0;j<num_column;j++){
			Double entropr_column_j = entropy(j);
			entropys_column.add(entropr_column_j);
		}
		return  entropys_column;
	}	
	
	public double entropy(int column_index){
		double entropy = 0;
		HashMap<String,Integer> count_everyvalue_in_column = count_for_column(column_index);
		double total_number = knownledge_data.length;
		//HashMap<String,Double> pr_everyvalue_in_column = new HashMap<String,Double>();
		
		for(String key:count_everyvalue_in_column.keySet()){
			double count_everyvalue = count_everyvalue_in_column.get(key);
			//System.out.println(key+";"+count_everyvalue);
			double pr_everyvalue = count_everyvalue/total_number;
			//System.out.println(pr_everyvalue);
			entropy+=pr_everyvalue*Math.log(1/pr_everyvalue);
			//System.out.println(pr_everyvalue*Math.log(1/pr_everyvalue));
		}
		
		return entropy;
	}	
	
	

}
