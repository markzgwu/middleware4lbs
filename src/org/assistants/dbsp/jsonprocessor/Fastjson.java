package org.assistants.dbsp.jsonprocessor;

import org.apache.commons.lang.StringUtils;
import org.parameters.I_constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.forFile.FileTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.framework.database.memory.File_for_memory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public final class Fastjson implements Interface_Json{
	private final static Logger logger = LoggerFactory.getLogger(Fastjson.class);
	private final static String filepath = I_constant.default_POIDB_filepath;
	
    public int ObtainNumPOIs(final String json_input_str){
    	final JSONObject jsonobject = JSON.parseObject(json_input_str);
		String total_str = jsonobject.getString("total");
		if(total_str==null){
			total_str="0";
		}
		int total = Integer.parseInt(total_str);
		logger.debug("TOTAL:"+total);
		jsonobject.clear();
		return total;
    }
	
    public int ObtainResults_OnePage(final String json_input_str){
    	return ObtainResults_OnePage_v1(json_input_str);
    }
    
    public int ObtainResults_OnePage_v2(final String json_input_str){
    	final JSONObject jsonobject = JSON.parseObject(json_input_str);
		final JSONArray jsonarray = jsonobject.getJSONArray("results");
		int ObtainResults = 0;
		if(jsonarray!=null){
			ObtainResults = jsonarray.size();
		}
			for(int j = 0; j<ObtainResults ; j++  ){
				final JSONObject tempJSONObj = jsonarray.getJSONObject(j);
				final String content = tempJSONObj.toJSONString();
				FileTool.saveOneLinetoFile(filepath,content);
				logger.debug(content);
			}
			jsonobject.clear();
		return ObtainResults;
    }    
    
    public int ObtainResults_OnePage_v1(final String json_input_str){
    	final JSONObject jsonobject = JSON.parseObject(json_input_str);
		final JSONArray jsonarray = jsonobject.getJSONArray("results");
			final int ObtainResults = jsonarray.size();
			for(int j = 0; j<ObtainResults ; j++  ){
				final JSONObject tempJSONObj = jsonarray.getJSONObject(j);
				final String uid = tempJSONObj.getString("uid");
				if(uid == null){
					logger.warn("NULL UID:"+tempJSONObj.toJSONString());
				}else{
					final String content = tempJSONObj.toJSONString();
					FileTool.saveOneLinetoFile(filepath,content);
					logger.debug(content);
				}
			}
			jsonobject.clear();
		return ObtainResults;
    }
    
    public final static POIentry parsePOIfromJson(final String jsonstring_for_onePOI){
    	final POIentry onepoi=new POIentry();
    	final JSONObject jsonobject = JSON.parseObject(jsonstring_for_onePOI);
		
    	String uid_str = jsonobject.getString("uid");
		if(StringUtils.isEmpty(uid_str)){
			return null;
		}
		onepoi.setUid(uid_str);
		
		JSONObject location_obj = jsonobject.getJSONObject("location");
		String lng_x = location_obj.getString("lng");
		String lat_y = location_obj.getString("lat");
		String lat_y_lng_x = lat_y+","+lng_x;
		logger.debug(lat_y_lng_x);
		LocationCoordinate loc = new LocationCoordinate(lat_y_lng_x);
		onepoi.setLocation(loc);
		
		String address = jsonobject.getString("address");
		onepoi.setAddress(address);
		
		String name = jsonobject.getString("name");
		onepoi.setName(name);
		
		onepoi.setData(jsonstring_for_onePOI);
    	return onepoi;
    }
    
	public static void main(String[] args) throws Exception{
		String json_line = File_for_memory.ReadJsonByLineNum(filepath, 0);
		System.out.println(json_line);
	}

}
