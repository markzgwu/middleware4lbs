package measure.AuthKBA.experiments;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

public class AbstractData {
	protected String[][] knownledge_data;
	
	protected double g(int column_index){
		double g = g_sophisticated(column_index);
		return g;
	}
	
	protected double g_generic_sup(int column_index,boolean b){
		double g_generic = 0;
		
		if(b){
			g_generic = this.g_deterministic(column_index);
		}else{
			g_generic = this.g_uniform(column_index);
		}
		
		return g_generic;
	}	
	
	protected double g_uniform(int column_index){
		double g1 = 1;
		
		HashMap<String,Integer> counts_every_value = count_for_column(column_index);
		double a = counts_every_value.size();
		g1 = 1/a;
		
		return g1;
	}	
	
	protected double g_sophisticated(int column_index){
		double g2 = 0;
		
		HashMap<String,Integer> counts_every_value = count_for_column(column_index);
		double m = knownledge_data.length;
		
		for(String value:counts_every_value.keySet()){
			double pr = counts_every_value.get(value)/m;
			//System.out.println(pr);
			g2+=(pr*pr);
		}
		
		return g2;
	}
	
	protected double g_deterministic(int column_index){
		double g3 = 0;
		
		HashMap<String,Integer> counts_every_value = count_for_column(column_index);
		double m = knownledge_data.length;
		
		for(String value:counts_every_value.keySet()){
			double pr = counts_every_value.get(value)/m;
			if(pr>g3){
				g3=pr;
			}
		}
		
		return g3;
	}	
	
	protected String average_value_if_max_count_not_empty(int column_index){
		//double g3 = 0;
		String value1 = "";
		HashMap<String,Integer> counts_every_value = count_for_column(column_index);
		double m1 = 0;
		//double m2 = 0;
		
		for(String value:counts_every_value.keySet()){
			double count_not_empty = counts_every_value.get(value);
			if(StringUtils.isNotEmpty(value)){
				m1+=count_not_empty;
				
			}
		}
		return value1;
	}	
	
	protected String value_if_max_count_not_empty(int column_index){
		double g3 = 0;
		String value1 = "";
		HashMap<String,Integer> counts_every_value = count_for_column(column_index);
		double m = knownledge_data.length;
		
		for(String value:counts_every_value.keySet()){
			double pr = counts_every_value.get(value)/m;
			if((pr>g3)&&(StringUtils.isNotEmpty(value))){
				g3=pr;
				value1=value;
			}
		}
		return value1;
	}	
	
	protected String value_if_max_count(int column_index){
		double g3 = 0;
		String value1 = "";
		HashMap<String,Integer> counts_every_value = count_for_column(column_index);
		double m = knownledge_data.length;
		
		for(String value:counts_every_value.keySet()){
			double pr = counts_every_value.get(value)/m;
			if(pr>g3){
				g3=pr;
				value1=value;
			}
		}
		return value1;
	}	
	
	protected HashMap<String,Integer> count_for_column(int column_index){
		HashMap<String,Integer> values_column = new HashMap<String,Integer>();
		for(String[] row_data: knownledge_data){
			Integer init_value = 1;
			String key=row_data[column_index];//
			boolean existed = values_column.containsKey(key);
			if(existed){
				int count = values_column.get(key);
				//System.out.println(count);
				count++;
				//System.out.println(count);
				//values_column.remove(key);
				values_column.put(key, count);
				
			}else{
				values_column.put(key, init_value);
			}

		}

		
		/*
		for(String key:values_column.keySet()){
			System.out.println(key+";"+values_column.get(key));
		}
		System.out.println();
		*/
		
		return values_column;
	}
}
