package measure.AuthKBA.experiments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public final class AttackerGenerator extends CountData{
	
	public AttackerGenerator(String[][] input_dataset, ParameterPackage mypp) {
		super(input_dataset, mypp);
		// TODO Auto-generated constructor stub
	}	
	
	String[] attacker1(){
		//int m = knownledge_data.length;
		int n = knownledge_data[0].length;
		String[] attacker = new String[n];
		
		for(int j=0;j<n;j++){
			//attacker[j] = value_if_max_count(j);
			attacker[j] = value_if_max_count_not_empty(j);
			//System.out.println();
		}
		String out ="Attacker=[";
		for(String s:attacker){
			out+=s+",";
		}
		out+="]";
		System.out.println(out);
		return attacker;	
	}
	
	String[] attacker_determined(){
		//int m = knownledge_data.length;
		int n = knownledge_data[0].length;
		String[] attacker = new String[n];
		
		for(int j=0;j<n;j++){
			//attacker[j] = value_if_max_count(j);
			attacker[j] = value_if_max_count_not_empty(j);
			//System.out.println();
		}
		String out ="Attacker=[";
		for(String s:attacker){
			out+=s+",";
		}
		out+="]";
		System.out.println(out);
		return attacker;	
	}
	
	int random(int mode){
		int random = 0;
		long time  = System.currentTimeMillis(); 
		java.util.Random rg=new java.util.Random(time);
		random = rg.nextInt();
		random = random % mode;
		random = Math.abs(random);
		return random;
	}
	
	String[] attacker_sophisticated(){
		int n = knownledge_data[0].length;
		int random_imposter = random(n);
		String[] attacker = knownledge_data[random_imposter];
		String out ="Attacker=[";
		for(String s:attacker){
			out+=s+",";
		}
		out+="]";
		System.out.println(out);
		return attacker;	
	}	
	
	String randomvalue_select(int columnindex){
		String randomvalue = "";
		HashMap<String,Integer> counts_every_value = count_for_column(columnindex);
		Set<String> values1 = counts_every_value.keySet();
		
		ArrayList<String> values = new ArrayList<String>(values1);
		
		/*if(values.contains("null")){
			values.remove(null);
			values.add("0");
		}*/
		
		//System.out.println(values.toString());
		//Collections.sort(values);
		//System.out.println(values.toString());
		int mode = values.size();
		int select_random_index = random(mode);
		
		//System.out.println(select_random_index);
		
		randomvalue = values.get(select_random_index);
		/*if(StringUtils.equals(randomvalue, "0")){
			randomvalue = null;
		}*/
		return randomvalue;
	}
	
	String[] attacker_uniform(){
		//int m = knownledge_data.length;
		int n = knownledge_data[0].length;
		String[] attacker = new String[n];
		
		for(int j=0;j<n;j++){
			//attacker[j] = value_if_max_count(j);
			attacker[j] = randomvalue_select(j);
		}
		String out ="Attacker=[";
		for(String s:attacker){
			out+=s+",";
		}
		out+="]";
		System.out.println(out);
		return attacker;	
	}
	
	String[] attacker2(){
		//int m = knownledge_data.length;
		int n = knownledge_data[0].length;
		String[] attacker = new String[n];
		
		for(int j=0;j<n;j++){
			//attacker[j] = value_if_max_count(j);
			attacker[j] = value_if_max_count_not_empty(j);
			//System.out.println();
		}
		String out ="Attacker=[";
		for(String s:attacker){
			out+=s+",";
		}
		out+="]";
		System.out.println(out);
		return attacker;
		
	}
}
