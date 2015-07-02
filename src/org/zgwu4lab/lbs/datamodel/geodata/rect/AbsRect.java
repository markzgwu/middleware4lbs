package org.zgwu4lab.lbs.datamodel.geodata.rect;

import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;

import com.alibaba.fastjson.JSON;

public abstract class AbsRect {
	public LocationCoordinate left_bottom = null;
	public LocationCoordinate right_top = null;
	public AbsRect(LocationCoordinate left_bottom,LocationCoordinate right_top){
		this.left_bottom = left_bottom;
		this.right_top = right_top;
	}
	
	public AbsRect(String rect_str_lbXY_rtXY){
		Load_lbXY_rtXY(rect_str_lbXY_rtXY);
	}
	
	public String toString(){
		return JSON.toJSONString(this);
	}
	
	public final void Load_lbYX_rtYX(String rect_str_lbYX_rtYX){
		String[] coordinates = rect_str_lbYX_rtYX.split(",");
		if(coordinates.length==4){
			this.left_bottom = new LocationCoordinate(Double.parseDouble(coordinates[1]),Double.parseDouble(coordinates[0]));
			this.right_top = new LocationCoordinate(Double.parseDouble(coordinates[3]),Double.parseDouble(coordinates[2]));
		}else{
			this.left_bottom = null;
			this.right_top = null;
		}
	}
	
	public final void Load_lbXY_rtXY(String rect_str_lbXY_rtXY){
		String[] coordinates = rect_str_lbXY_rtXY.split(",");
		if(coordinates.length==4){
			this.left_bottom = new LocationCoordinate(Double.parseDouble(coordinates[0]),Double.parseDouble(coordinates[1]));
			this.right_top = new LocationCoordinate(Double.parseDouble(coordinates[2]),Double.parseDouble(coordinates[3]));
		}else{
			this.left_bottom = null;
			this.right_top = null;
		}

	}	
	
	public final double AREA(){
		double area_km = MapsTool.mapmeasure.getArea_Rect(left_bottom,right_top);
		return area_km;
	}
	
	public final boolean isWithinTheRectOrOnItsBounds(LocationCoordinate locationNode){
		boolean b = (left_bottom.lng_x<=locationNode.lng_x)&&(locationNode.lng_x<=right_top.lng_x);
		b = b && ( (left_bottom.lat_y<=locationNode.lat_y) && (locationNode.lat_y<=right_top.lat_y) );
		return b;
	}
	
	public final boolean isWithinTheRect(LocationCoordinate locationNode){
		boolean b = (left_bottom.lng_x<locationNode.lng_x)&&(locationNode.lng_x<right_top.lng_x);
		b = b && ( (left_bottom.lat_y<locationNode.lat_y) && (locationNode.lat_y<right_top.lat_y) );
		return b;
	}
	
	public final boolean isOnBoundsOfTheRect(LocationCoordinate locationNode){
		boolean b = false;
		if(isWithinTheRectOrOnItsBounds(locationNode)==true&&isWithinTheRect(locationNode)==false){
			b = true;
		}
		return b;
	}	
}
