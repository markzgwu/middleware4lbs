package org.assistants.dbsp.jsonprocessor;

public final class Json_Entry {
	
	public static Interface_Json json_processor(){
		//private final Interface_Json intjson = new Fastjson();
		final Interface_Json intjson = new Jsonsmart();		
		return intjson;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
