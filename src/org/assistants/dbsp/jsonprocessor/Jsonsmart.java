package org.assistants.dbsp.jsonprocessor;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.forFile.FileTool;

public final class Jsonsmart implements Interface_Json{
	private final Logger logger = LoggerFactory.getLogger(Jsonsmart.class);
	
    public int ObtainNumPOIs(final String json_input_str){
    	final JSONObject jsonobject = (JSONObject)JSONValue.parse(json_input_str);
		int total_int = (Integer) jsonobject.get("total");
		logger.debug("TOTAL:"+total_int);
		//System.out.println(total_int);
		jsonobject.clear();
		if(total_int<0){
			total_int=0;
		}
		return total_int;
    }   
    
    public int ObtainResults_OnePage(final String json_input_str){
    	int ObtainResults = 0;
    	if(json_input_str == null) {
    		return 0;
    	}
    	
    	final JSONObject jsonobject = (JSONObject) JSONValue.parse(json_input_str);
    	if(jsonobject==null){
    		return 0;
    	}
    	
    	JSONArray jsonarray = (JSONArray)jsonobject.get("results");
		if(jsonarray == null){
			return 0;
		}
		
		ObtainResults = jsonarray.size();
		
		for(int j = 0; j<ObtainResults ; j++  ){
			final JSONObject tempJSONObj = (JSONObject) jsonarray.get(j);
			final String uid = (String) tempJSONObj.get("uid");
			if(uid == null){
				logger.warn("NULL UID:"+tempJSONObj.toJSONString());
			}else{
				final String content = tempJSONObj.toJSONString();
				try {
					FileTool.saveOneLinetoFile("D:\\POIdatabase.json",content);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug(content);
			}
		}
		jsonobject.clear();
		
		return ObtainResults;
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
