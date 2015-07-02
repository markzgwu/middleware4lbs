package org.tools.forString;

import java.util.ArrayList;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class StringTool {
	//private final static Logger logger = LoggerFactory.getLogger(StringTool.class);
	public static String replaceLastChat(String str,String newlastchar){
		return str.replaceAll(".$", newlastchar);
	}
	
	public static String[] toStringArray(final ArrayList<String> data){
		final String[] dataset = data.toArray(new String[data.size()]);
		return dataset;
	}
	
	public static String[] toArray(final ArrayList<String> data){
		final String[] dataset = data.toArray(new String[data.size()]);
		return dataset;
	}
	
	public static String  output(final String[] fields){
		return ShowStringArray(fields);
	}
	
	public static void Show(final String[] fields){
		System.out.println(ShowStringArray(fields));
	}
	
	public static String fixsubfoldername(final int number){
		return fixsubfoldername(String.valueOf(number));
	}
	
	public static String fixsubfoldername(final String name){
		String subfoldername = name;
		for(int j = 0;j<(4-subfoldername.length());j++){
			subfoldername="0"+subfoldername;	
		}
		return subfoldername;
	}
	
	public static String ShowStringArray(final String[] array){
		String strarray = "";
		for(String s:array){
			strarray+=s+";";
		}
		return strarray;
	}
	
	public static String ShowStringArray(final ArrayList<String> array){
		String strarray = "";
		for(String s:array){
			strarray+=s+";";
		}
		return strarray;
	}
	
	public static String ShowIntegerArray(final ArrayList<Integer> array){
		String strarray = "";
		for(Integer s:array){
			strarray+=s+";";
		}
		return strarray;
	}
	
	public static String[] split(final String str, final String sign) {
	      final String[] strarray=str.split(sign);
	      //logger.debug(str+";"+strarray.length+";"+ShowStringArray(strarray));
	      return strarray;
	}
	
	public static void main(String[] args) {
		  System.out.println(args.length); 
	      final String str="40.009021,116.321625"; 
	      String[] strarray=split(str,"\\,");
	      System.out.println(strarray.length); 
	      for (int i = 0; i < strarray.length; i++) {
	    	  System.out.println(strarray[i]); 
	      }
	      
	      System.out.println(fixsubfoldername(10));
	          
	}

}
