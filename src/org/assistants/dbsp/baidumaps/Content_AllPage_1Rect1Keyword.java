package org.assistants.dbsp.baidumaps;

import java.io.IOException;
import java.util.ArrayList;

import org.assistants.dbsp.jsonprocessor.Jsonsmart_OnePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.URLTool;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public final class Content_AllPage_1Rect1Keyword {
	
	private final Logger logger = LoggerFactory.getLogger(Content_AllPage_1Rect1Keyword.class);
	
	public Content_AllPage_1Rect1Keyword(Arguments_URL current_arguments_url){
		arguments = current_arguments_url;
	}	
	
	private final Arguments_URL arguments;
	public final Arguments_URL getArguments() {
		return arguments;
	}

	private final ArrayList<String> POIs_Array = new ArrayList<String>();
	public final ArrayList<String> getPOIs_Array() {
		return POIs_Array;
	}

	public void load(){
		int results_num_1rect1keyword = ObtainPOIs_AllPage_1Rect1Keyword();
		logger.debug("POIs:"+results_num_1rect1keyword+";"+arguments.bounds+";"+arguments.query);
	}
	
    private int ObtainPOIs_AllPage_1Rect1Keyword(){
		int results_size_all = 0;
		
		int page_num = 0;
		String jsoncontent = ReadContent_OnePage(page_num);
		Jsonsmart_OnePage json_onepage = new Jsonsmart_OnePage(jsoncontent);
		int total = json_onepage.ObtainNumPOIs();
		
		boolean b = (total>760);
		if(b){
			System.out.println("Excessive POIs!{total:"+total+"}"+this.arguments.bounds+";"+this.arguments.query);
			SplitRect();
		}else{
			int ObtainResults = 0;
			do{
				int n = json_onepage.parse();
				final ArrayList<String> POIs_1Page = json_onepage.getPOIJson_Array();
				String debug_msg = "# of POIs:"+n;
				//System.out.println(debug_msg);
				logger.debug(debug_msg);
				if(POIs_1Page==null){
					logger.warn("POIs_1Page is NULL!");
					break;
				}else{
					POIs_Array.addAll(POIs_1Page);
					ObtainResults = POIs_1Page.size();
					results_size_all += ObtainResults;
					jsoncontent = ReadContent_OnePage(page_num);
					json_onepage = new Jsonsmart_OnePage(jsoncontent);
					page_num++;					
				}

			}while(ObtainResults>1);
		}

		return results_size_all;
    }	
    
    private int SplitRect(){
    	System.out.println("Spliting the Rect!");
    	String bounds = arguments.bounds;
    	RectBounds smaller_rect = new RectBounds(arguments.g.zoom());
    	smaller_rect.Load_lbYX_rtYX(bounds);
    	final ArrayList<String> rectlist = smaller_rect.getRectList();
    	final ArrayList<String> more_results = ObtainResults_SmallerRect(rectlist,smaller_rect);
    	POIs_Array.addAll(more_results);
    	return more_results.size();
    }

	private ArrayList<String> ObtainResults_SmallerRect(ArrayList<String> rect_list, RectBounds smallerrect) {
		int grid_num = 0;
		ArrayList<String> More_POI_Array = new ArrayList<String>();
		for(String rect_str:rect_list){
			int NumResults_Per_Request = 0;
			Arguments_URL smallerrect_url = new Arguments_URL(rect_str,arguments.query);
			smallerrect_url.g=smallerrect.granularity;
	    	final Content_AllPage_1Rect1Keyword temp = new Content_AllPage_1Rect1Keyword(smallerrect_url);
	    	temp.load();
	    	ArrayList<String> temp_POI_Array = temp.getPOIs_Array();				
			int NumResults_OneGrid_OneKeyword = temp_POI_Array.size();
			NumResults_Per_Request+=NumResults_OneGrid_OneKeyword;
			System.out.println("Smaller RECT(lbYX,rtYX):"+grid_num+";"+rect_str+";"+smallerrect_url.query+"#:"+NumResults_Per_Request);
			grid_num++;
			//if(grid_num>100)break;
			More_POI_Array.addAll(temp_POI_Array);
		}
		return More_POI_Array;
	}    
	
	private String ReadContent_OnePage(int page_num){
		String output = null;
		try {
			output = URLTool.ReadContent_FromURL(arguments.toURL(page_num));
			logger.debug(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}    
}
