package org.zgwu4lab.lbs.datamodel.geodata;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgwu4lab.lbs.datamodel.geodata.measure.*;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.RectBounds;

public final class MapsTool{
	private static final Logger logger = LoggerFactory.getLogger(MapsTool.class);

	public  static String log = ""; 
	/**
	longitude ¾­¶È
	latitude Î¬¶È
	*/
	final public static AbsMapDistance mapmeasure = new MapDistanceA1();
	//final public static AbsMapDistance mapmeasure = new MapDistanceGoogle();
	
	public static ArrayList<String> SplitRectBounds(RectBounds rect,Granularity g){
		ArrayList<String> rect_list = new ArrayList<String>();
		double x_n = Math.round((rect.right_top.lng_x - rect.left_bottom.lng_x)/g.lng_X);
		double y_n = Math.round((rect.right_top.lat_y - rect.left_bottom.lat_y)/g.lat_Y);
		
		logger.debug("y_n"+y_n+";x_n"+x_n);
		
		LocationCoordinate tmp_left_bottom = new LocationCoordinate(0,0);
		LocationCoordinate tmp_right_top = new LocationCoordinate(0,0);
		for(int y = 0;y<y_n;y++){
			for(int x = 0;x<x_n;x++){
				tmp_left_bottom.lng_x=rect.left_bottom.lng_x+x*g.lng_X;
				tmp_left_bottom.lat_y=rect.left_bottom.lat_y+y*g.lat_Y;
				
				tmp_right_top.lng_x=tmp_left_bottom.lng_x+g.lng_X;
				tmp_right_top.lat_y=tmp_left_bottom.lat_y+g.lat_Y;
				
				String rect_str_YX = tmp_left_bottom.toLocation_yx()+","+tmp_right_top.toLocation_yx();
				rect_list.add(rect_str_YX);
			}
		}
		return rect_list;
	}	
	
	public static void main(String[] args){
		LocationCoordinate left_bottom = new LocationCoordinate(116.138436,39.468722);
		LocationCoordinate right_top = new LocationCoordinate(117.138436,40.468722);
		double area = MapsTool.mapmeasure.getArea_Rect_DiffLat(left_bottom, right_top);
		logger.info("AREA:"+area);
		double area1 = MapsTool.mapmeasure.getArea_Rect(left_bottom, right_top);
		logger.info("AREA:"+area1);;
	}
	
}
