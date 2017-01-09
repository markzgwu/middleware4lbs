package org.assistants.dbsp.baidumaps;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.URLTool;
import org.tools.forFile.FileTool;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public final class Loader_POIs{
	private final Logger logger = LoggerFactory.getLogger(Loader_POIs.class);
	private final Task cur_task = new Task();
	private final RectBounds cur_rectbounds;
	public final ArrayList<TaskContext> DependingRects = new ArrayList<TaskContext>();
	
	public Loader_POIs(RectBounds rb){
		cur_rectbounds = rb;
	}
	
	private String ReadContent_OnePage(int page_num){
		String output = null;
		try {
			output = URLTool.ReadContent_FromURL(cur_task.toURL(page_num));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
    
    private int ObtainNumPOIs(JSONObject jsonobject){
		String total_str = jsonobject.getString("total");
		if(total_str==null){
			total_str="0";
		}
		int total = Integer.parseInt(total_str);
		logger.debug("TOTAL:"+total);
		return total;
    }
    
    private int ObtainResults_OnePage(JSONObject jsonobject){
    	int ObtainResults = 0;
		JSONArray jsonarray = jsonobject.getJSONArray("results");
		//logger.debug(jsonarray.toJSONString());

			ObtainResults = jsonarray.size();
			for(int j = 0; j<ObtainResults ; j++  ){
				JSONObject tempJSONObj = jsonarray.getJSONObject(j);
				String uid = tempJSONObj.getString("uid");
				if(uid == null){
					logger.warn("NULL UID:"+tempJSONObj.toJSONString());
				}else{
					String content = tempJSONObj.toJSONString();
					try {
						FileTool.appendFile("D:\\POIdatabase.json",content+"\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.debug(content);
				}
			}			
		return ObtainResults;
    }
    
    private int ObtainResults_OneGrid_OneKeyword(){
		int results_size_all = 0;
		int ObtainResults = 0;
		
		String jsoncontent = ReadContent_OnePage(0);
		JSONObject jsonobject = JSON.parseObject(jsoncontent);
		int total = ObtainNumPOIs(jsonobject);
		boolean b = (total>760);
		if(b){
			TaskContext tc = new TaskContext(cur_task.bounds, cur_task.query_keywords);
			DependingRects.add(tc);
			System.out.println("Too many POIs! NUM of Results on SP (Total):"+total);
		}else{
			
			ObtainResults = ObtainResults_OnePage(jsonobject);
			logger.debug("total="+total+";b="+b+";ObtainResults:"+ObtainResults);
			results_size_all+=ObtainResults;
			
			int page_num = 1;
			int ObtainResultsOther = 0;
			do{
				String jsoncontent_otherresults = ReadContent_OnePage(page_num);
				JSONObject jsonobject_otherresults = JSON.parseObject(jsoncontent_otherresults.trim());
				//System.out.println(jsoncontent_otherresults);
				ObtainResultsOther = ObtainResults_OnePage(jsonobject_otherresults);
				logger.debug("total="+total+";b="+b+";ObtainOtherResults:"+ObtainResultsOther);
				results_size_all+=ObtainResultsOther;
				page_num++;
				jsonobject_otherresults.clear();
				jsonobject_otherresults = null;
			}while(ObtainResultsOther>1);
			
		}
		
		logger.debug("Worker Obtain The number of Results:"+results_size_all);
		jsonobject.clear();
		jsonobject=null;

		return results_size_all;
    }
    
    public void do_main(){
    	ObtainResults_Grids(cur_rectbounds.getRectList());
    }
    
    String printKeywords(String[] keywords){
    	String printKeywords = "";
    	for(String k: keywords){
    		printKeywords+=k+";";
    	}
    	return printKeywords;
    }
    
	private void ObtainResults_Grids(ArrayList<String> rect_list) {

		final String[] keywords = {"学校","饭店","银行"};
		//final String[] keywords = {"银行"};
		String print_keywords = printKeywords(keywords);
		int grid_num = 0;
		for(String rect_str:rect_list){
			cur_task.bounds=rect_str;
			int NumResults_Per_Request = 0;
			for(String keyword:keywords){
				cur_task.query_keywords=keyword;
				int NumResults_OneGrid_OneKeyword = ObtainResults_OneGrid_OneKeyword();
				NumResults_Per_Request+=NumResults_OneGrid_OneKeyword;
			}
			//Coordinate(lat_y,lnt_x);
			System.out.println("RECT(lbYX,rtYX):"+grid_num+";"+rect_str+";"+print_keywords+"NUM of Results:"+NumResults_Per_Request);
			grid_num++;
			//if(grid_num>100)break;
		}
	}	
}
