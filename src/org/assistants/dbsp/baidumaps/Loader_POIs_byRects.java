package org.assistants.dbsp.baidumaps;

import java.util.ArrayList;

import org.parameters.I_constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.ArrayTool;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public final class Loader_POIs_byRects{
	private final Logger logger = LoggerFactory.getLogger(Loader_POIs_byRects.class);

	private final RectBounds cur_rectbounds;
	final String[] keywords = I_constant.keywords;
	
	public Loader_POIs_byRects(RectBounds rect){
		cur_rectbounds = rect;
	}
    
    private int SavePOIs_1rect1keyword(Arguments_URL arguments){
    	final Content_AllPage_1Rect1Keyword temp = new Content_AllPage_1Rect1Keyword(arguments);
    	temp.load();
    	ArrayList<String> POI_Array = temp.getPOIs_Array();
		int results_size_all = POI_Array.size();
		save(POI_Array);
		logger.debug("Worker Obtain The number of Results:"+results_size_all);
		return results_size_all;
    }
    
    private void save(ArrayList<String> array_str){
    	final String savefilepath = I_constant.default_POIDB_filepath;
    	ArrayTool.ArraytoFile(savefilepath, array_str);
    }
    
    public void do_main(){
    	final ArrayList<String> rectlist = cur_rectbounds.getRectList();
    	ObtainResults_Grids(rectlist);
    }
    
	private void ObtainResults_Grids(ArrayList<String> rect_list) {

		
		//final String[] keywords = {"ѧУ"};
		//String print_keywords = printKeywords(keywords);
		int grid_num = 0;
		for(String rect_str:rect_list){
			int NumResults_Per_Request = 0;
			String print_keywords = "";
			for(String keyword:keywords){
				Arguments_URL cur_url = new Arguments_URL(rect_str,keyword);
				cur_url.g=cur_rectbounds.granularity;
				int NumResults_OneGrid_OneKeyword = SavePOIs_1rect1keyword(cur_url);
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
