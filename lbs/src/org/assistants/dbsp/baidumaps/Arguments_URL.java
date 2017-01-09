package org.assistants.dbsp.baidumaps;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.datamodel.geodata.Granularity;

public final class Arguments_URL {
	final String myak = I_constant.myak;
	public String SP_URL = I_constant.SP_URL;
	public String page_size = I_constant.page_size;
	
	final public String bounds;
	//public String region = "±±¾©";
	final public String query;
	
	public Arguments_URL(String rectbounds,String querykeyword){
		bounds = rectbounds;
		query = querykeyword;
	}
	
	private String toURL(){
		String URL = SP_URL+"&output=json&ak="+myak;
		URL+="&bounds="+bounds;
		//URL+="&region="+region;
		URL+="&query="+query;
		URL+="&page_size="+page_size;
		URL+="&page_num=";
		return URL;
	}
	public String toURL(int page_num) {
		return toURL()+page_num;
	}
	
	public Granularity g=null;
	
}
