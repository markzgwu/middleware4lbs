package org.assistants.dbsp.jsonprocessor;

import java.util.ArrayList;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Jsonsmart_OnePage extends AbsContent{
	private final Logger logger = LoggerFactory.getLogger(Jsonsmart_OnePage.class);
	
	public Jsonsmart_OnePage(String json_input_str) {
		super(json_input_str);
	}
	
    public int ObtainNumPOIs(){
    	final JSONObject jsonobject = (JSONObject)JSONValue.parse(OriginalContent);
    	if(jsonobject==null){
    		logger.warn("jsonobject is NULL!");
    		return 0;
    	}
    	Object total = jsonobject.get("total");
    	if(total == null){
    		logger.warn("Json Domain (total) is NULL!");
    		return 0;
    	}
		int total_int = (Integer) jsonobject.get("total");
		logger.debug("TOTAL:"+total_int);
		//System.out.println(total_int);
		jsonobject.clear();
		if(total_int<0){
			total_int=0;
		}
		return total_int;
    }
    
    protected ArrayList<String> POIJson_Array;
    
    public final ArrayList<String> getPOIJson_Array() {
		return POIJson_Array;
	}

	public int parse(){
		int r = 0;
    	POIJson_Array = ObtainResultsToArray_OnePage();
    	if(POIJson_Array==null){
    		logger.warn("POIJson_Array is NULL!");
    	}else{
    		r = POIJson_Array.size();
    	}
		return r;
    }
    
    private ArrayList<String> ObtainResultsToArray_OnePage(){
    	
    	if(OriginalContent == null) {
    		return null;
    	}
    	
    	final JSONObject jsonobject = (JSONObject) JSONValue.parse(OriginalContent);
    	if(jsonobject==null){
    		return null;
    	}
    	
    	final JSONArray jsonarray = (JSONArray)jsonobject.get("results");
		if(jsonarray == null){
			return null;
		}
		
		final int ObtainResults = jsonarray.size();
		final ArrayList<String> ObtainResults_OnePage = new ArrayList<String>();
		for(int j = 0; j<ObtainResults ; j++  ){
			final JSONObject tempJSONObj = (JSONObject) jsonarray.get(j);
			final String uid = (String) tempJSONObj.get("uid");
			if(uid == null){
				logger.warn("NULL UID:"+tempJSONObj.toJSONString());
			}else{
				final String content = tempJSONObj.toJSONString();
				ObtainResults_OnePage.add(content);
				logger.debug(content);
			}
		}
		jsonobject.clear();
		
		return ObtainResults_OnePage;
    }
    
    public String OutputPOIs(){
    	return  OutputLines(POIJson_Array);
    }
    
    private String OutputLines(ArrayList<String> POIJson_Array){
    	final StringBuffer buf_Save = new StringBuffer();
    	for(String POIJson:POIJson_Array){
    		buf_Save.append(POIJson+"\n");
    	}
    	return buf_Save.toString();
    }
    
	public static void main(String[] args) {
		  System.out.println("=======decode=======");
        
		  String s="[0,{'1':{'2':{'3':{'4':[5,{'6':7}]}}}}]";
		  Object obj=JSONValue.parse(s);
		  JSONArray array=(JSONArray)obj;
		  System.out.println("======the 2nd element of array======");
		  System.out.println(array.get(1));
		  System.out.println();
		                
		  JSONObject obj2=(JSONObject)array.get(1);
		  System.out.println("======field \"1\"==========");
		  System.out.println(obj2.get("1"));

		  s="{}";
		  obj=JSONValue.parse(s);
		  System.out.println(obj);                

		  s="{\"key\":\"Value\"}";
		  // JSONValue.parseStrict()
		  // can be use to be sure that the input is wellformed
		  try {
			obj=JSONValue.parseStrict(s);
			  JSONObject obj3=(JSONObject)obj;
			  System.out.println("====== Object content ======");
			  System.out.println(obj3.get("key"));
			  System.out.println();			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
