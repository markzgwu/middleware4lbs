package org.assistants.dbsp.queries;

import java.io.IOException;

public final class URL4LBS_GeocodingAPI extends AbsURL4LBS{
	
	public URL4LBS_GeocodingAPI(String loc) {
		super(loc);
		// TODO Auto-generated constructor stub
	}

	final String myak = "C43da03d1baa7160e20bdee8bf9d403d";
	public String SP_URL = "http://api.map.baidu.com/geocoder/v2/?pois=1";
	public String location_URL = "location="+location;
	public String getURL(){
		String URL = SP_URL+"&output=json&ak="+myak;
		URL+="&"+location_URL;
		return URL;
	}
	
	public String accessurl(){

		String output = null;
		try {
			output = readContentFromGet(getURL());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public String operation() {
		String output = accessurl();
		System.out.println(output);
		return output;
	}
    
	public static void main(String[] args) {
		AbsURL4LBS client = new URL4LBS_GeocodingAPI("39.9507527,116.4296827");
		client.operation();
	}	
}
