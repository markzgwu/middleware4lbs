package org.assistants.dbsp.baidumaps;

public final class Task {
	final String myak = "C43da03d1baa7160e20bdee8bf9d403d";
	public String SP_URL = "http://api.map.baidu.com/place/v2/search?";
	String bounds = "39.988112,116.308402,40.006241,116.330536";
	public String region = "北京";
	public String query_keywords = "大学";
	public String page_size = "20";

	public String toURL(){
		String URL = SP_URL+"&output=json&ak="+myak;
		URL+="&bounds="+bounds;
		URL+="&region="+region;
		URL+="&query="+query_keywords;
		URL+="&page_size="+page_size;
		URL+="&page_num=";
		return URL;
	}

	public String toURL(int page_num) {
		return toURL()+page_num;
	}
}
