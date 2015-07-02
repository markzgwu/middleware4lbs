package org.assistants.dbsp.jsonprocessor;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgwu4lab.lbs.datamodel.json.JsonPOI;

import com.alibaba.fastjson.JSON;

public final class Jsonsmart_OneLine extends AbsContent{
	private final Logger logger = LoggerFactory.getLogger(Jsonsmart_OneLine.class);
	public Jsonsmart_OneLine(String json_input_str) {
		super(json_input_str);
	}
	
    public String UIDFromOneLine(){
    	final JSONObject jsonobject = (JSONObject)JSONValue.parse(OriginalContent);
    	if(jsonobject==null){
    		logger.warn("jsonobject is NULL!");
    		return null;
    	}
    	String uid = (String) jsonobject.get("uid");
    	if(uid == null){
    		logger.warn("Json Domain (uid) is NULL!");
    		return null;
    	}
		return uid;
    }
    
    public JsonPOI POIFromOneLIne(){
    	JsonPOI poi = JSON.parseObject(this.OriginalContent, JsonPOI.class);
		return poi;
    }
    
    
}
