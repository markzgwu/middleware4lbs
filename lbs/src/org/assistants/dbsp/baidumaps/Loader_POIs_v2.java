package org.assistants.dbsp.baidumaps;

import java.io.IOException;
import java.util.ArrayList;

import org.assistants.dbsp.jsonprocessor.Interface_Json;
import org.assistants.dbsp.jsonprocessor.Json_Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.URLTool;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public final class Loader_POIs_v2{
	private final Logger logger = LoggerFactory.getLogger(Loader_POIs_v2.class);
	private final Task cur_task = new Task();
	private final RectBounds cur_rectbounds;
	public final ArrayList<TaskContext> DependingRects = new ArrayList<TaskContext>();
	private final Interface_Json intjson = Json_Entry.json_processor();
	
	public Loader_POIs_v2(RectBounds rect){
		cur_rectbounds = rect;
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
    
    private int ObtainNumPOIs(final String json_input_str){
		return intjson.ObtainNumPOIs(json_input_str);
    }
    
    private int ObtainResults_OnePage(final String json_input_str){
    	return intjson.ObtainResults_OnePage(json_input_str);
    }
    
    private int ObtainResults_OneGrid_OneKeyword(){
		int results_size_all = 0;
		int ObtainResults = 0;
		
		String jsoncontent = ReadContent_OnePage(0);

		int total = ObtainNumPOIs(jsoncontent);
		boolean b = (total>760);
		if(b){
			TaskContext tc = new TaskContext(cur_task.bounds, cur_task.query_keywords);
			DependingRects.add(tc);
			System.out.println("Too many POIs! NUM of Results on SP (Total):"+total);
		}else{
			
			ObtainResults = ObtainResults_OnePage(jsoncontent);
			logger.debug("total="+total+";b="+b+";ObtainResults:"+ObtainResults);
			results_size_all+=ObtainResults;
			
			int page_num = 1;
			int ObtainResultsOther = 0;
			do{
				String jsoncontent_otherresults = ReadContent_OnePage(page_num);
				//System.out.println(jsoncontent_otherresults);
				ObtainResultsOther = ObtainResults_OnePage(jsoncontent_otherresults);
				logger.debug("total="+total+";b="+b+";ObtainOtherResults:"+ObtainResultsOther);
				results_size_all+=ObtainResultsOther;
				page_num++;
			}while(ObtainResultsOther>1);
			
		}
		logger.debug("Worker Obtain The number of Results:"+results_size_all);
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

		final String[] keywords = {"饭店","银行","旅游","车站","学校"};
		//final String[] keywords = {"学校"};
		//String print_keywords = printKeywords(keywords);
		int grid_num = 0;
		for(String rect_str:rect_list){
			cur_task.bounds=rect_str;
			int NumResults_Per_Request = 0;
			String print_keywords = "";
			for(String keyword:keywords){
				cur_task.query_keywords=keyword;
				int NumResults_OneGrid_OneKeyword = ObtainResults_OneGrid_OneKeyword();
				print_keywords+=keyword+":"+NumResults_OneGrid_OneKeyword+";";
				NumResults_Per_Request+=NumResults_OneGrid_OneKeyword;
				
			}
			//Coordinate(lat_y,lnt_x);
			System.out.println("RECT(lbYX,rtYX):"+grid_num+";"+rect_str+";"+print_keywords+"NUM of Results:"+NumResults_Per_Request);
			grid_num++;
			//if(grid_num>100)break;
		}
	}
}
