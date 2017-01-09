package org.assistants.dbsp.baidumaps;
import java.util.ArrayList;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.datamodel.geodata.Granularity;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;


public class Program_Entry {
	
	static void info(){
				
	}
	
	public static void test1(String[] args) {
		//LocationCoordinate left_bottom = new LocationCoordinate(116.217709,39.811036);
		//LocationCoordinate right_top = new LocationCoordinate(116.575306,40.047402);
		
		LocationCoordinate left_bottom = I_constant.downloader_left_bottom;
		LocationCoordinate right_top =I_constant.downloader_right_top;
		Granularity g1 = I_constant.downloader_granularity;
		
		RectBounds rect = new RectBounds(left_bottom,right_top,g1);
		rect.info();
		Loader_POIs_v2 loader = new Loader_POIs_v2(rect);
		loader.do_main();
		ArrayList<TaskContext> dependingrects = loader.DependingRects;
		System.out.println("NUM of Rects that has Exceeded POIs:"+dependingrects.size());
		Granularity g2 = new Granularity(0.01,0.01);
		for(TaskContext tc:dependingrects){
			System.out.println(""+tc.bounds_lbYX_rtYX+";"+tc.query_keywords);
			RectBounds rect1 = new RectBounds(g2);
			rect1.Load_lbYX_rtYX(tc.bounds_lbYX_rtYX);
			Loader_POIs_v2 loader1 = new Loader_POIs_v2(rect1);
			loader1.do_main();
		}
		
	}	
	
	public static void test3(String[] args) {
		LocationCoordinate left_bottom = new LocationCoordinate(116.427995,39.905773);
		LocationCoordinate right_top = new LocationCoordinate(116.452995,39.930773);
		Granularity g1 = new Granularity(0.01,0.01);
		
		RectBounds rect = new RectBounds(left_bottom,right_top,g1);
		rect.info();
		Loader_POIs_byRects loader = new Loader_POIs_byRects(rect);
		loader.do_main();

		
	}		
	
	public static void test2(String[] args) {
		//LocationCoordinate left_bottom = new LocationCoordinate(116.052995,39.680773);
		//LocationCoordinate right_top = new LocationCoordinate(117.138436,40.468722);
		
		//LocationCoordinate left_bottom = new LocationCoordinate(116.217709,39.811036);
		//LocationCoordinate right_top = new LocationCoordinate(116.575306,40.047402);
		//Granularity g1 = new Granularity(0.05,0.05);
		
		LocationCoordinate left_bottom = I_constant.downloader_left_bottom;
		LocationCoordinate right_top =I_constant.downloader_right_top;
		Granularity g1 = I_constant.downloader_granularity;
		
		//Granularity g1 = I_constant.default_granularity;
		RectBounds rect = new RectBounds(left_bottom,right_top,g1);
		rect.info();
		Loader_POIs_byRects loader = new Loader_POIs_byRects(rect);
		loader.do_main();

		
	}	
	

	
	public static void main(String[] args) {
		test2(args);
	}

}
