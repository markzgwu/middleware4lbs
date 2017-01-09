package org.zgwu4lab.lbs.datamodel.geodata;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;

public final class Granularity{
	public final double lng_X;
	public final double lat_Y;

	public Granularity(){
		lat_Y = 0.02;
		lng_X = 0.02;
	}
	
	public Granularity(double lng_X,double lat_Y){
		this.lat_Y = lat_Y;
		this.lng_X = lng_X;
	}
	
	public double AREA(){
		LocationCoordinate 	left_bottom = new LocationCoordinate(0,0);
		LocationCoordinate right_top = new LocationCoordinate(lng_X,lat_Y);
		double area_km = MapsTool.mapmeasure.getArea_Rect(left_bottom,right_top);
		return area_km;
	}
	
	public String output_YX(){
		return lat_Y+","+lng_X;
	}
	
	public Granularity zoom(){
		Granularity g = zoom(2);
		System.out.println("ZOOM: Generating a smaller Granularity "+g.output_YX());
		return g;
	}	
	
	public Granularity zoom(int zoom_ratio){
		double new_lat_Y = lat_Y/zoom_ratio;
		double new_lng_X = lng_X/zoom_ratio;
		return new Granularity(new_lng_X,new_lat_Y);
	}
}
