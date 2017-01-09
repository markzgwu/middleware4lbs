package org.zgwu4lab.lbs.datamodel.geodata.measure;

import org.tools.forLog.AbsLog;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;

public abstract class AbsMapDistance extends AbsLog{
	final protected double EARTH_RADIUS = 6378.137;
	//final protected double EARTH_RADIUS = 6371.004; //地球的平均半径 KM 6371.229
	protected abstract double getDistance(double lat1, double lng1, double lat2, double lng2);

	public double getDistance(LocationCoordinate loc1, LocationCoordinate loc2) {
        return getDistance(loc1.lat_y,loc1.lng_x,loc2.lat_y,loc2.lng_x);
	}
	
	public double toKMfromLatOffset(double offsetlat) {
		double r = 0;
		r = (Math.PI*EARTH_RADIUS)*offsetlat/180;
        return r;
	}
	
	public double toKMfromLngOffset(double offsetlng, double lat) {
		double r = 0;
		double lat_rad = lat*Math.PI/180;
		r = (Math.PI*EARTH_RADIUS*(Math.cos(lat_rad)))*offsetlng/180;
		logger.debug("lat:"+lat+";lat_rad:"+lat_rad+";"+Math.cos(lat_rad)+";");
        return r;
	}
	
	public double toKMfromlngOffset(LocationCoordinate center, double lngOffset){
		LocationCoordinate loc1 = center;
		LocationCoordinate loc2 = new LocationCoordinate(center.lng_x+lngOffset, center.lat_y);
		return getDistance(loc1,loc2);
	}
	
	public double toKMfromlatOffset(LocationCoordinate center, double latOffset){
		LocationCoordinate loc1 = center;
		LocationCoordinate loc2 = new LocationCoordinate(center.lng_x, center.lat_y+latOffset);
		return getDistance(loc1,loc2);
	}	
	
	public double getArea_Rect(LocationCoordinate left_bottom,LocationCoordinate right_top){
		return getArea_Rect_simply(left_bottom,right_top);
	}
	
	public double getArea_Rect_simply(LocationCoordinate left_bottom,LocationCoordinate right_top) {
		double lng1 = left_bottom.lng_x;//东经 X longitude 0-180
		double lat1 = left_bottom.lat_y;//北纬 Y latitude -90-0-+90
		double lng2 = right_top.lng_x;
		double lat2 = right_top.lat_y;
		double height = getDistance(lat1,lng1,lat2,lng1);
		double width = getDistance(lat2,lng1,lat2,lng2);
		double area = width*height;
		//String log = "height * width:"+height+"*"+width;
		//logger.info(log);
		return area;
	}
	
	public double getArea_Rect_DiffLat(LocationCoordinate left_bottom,LocationCoordinate right_top) {
		double lng1 = left_bottom.lng_x;//东经 X longitude 0-180
		double lat1 = left_bottom.lat_y;//北纬 Y latitude -90-0-+90
		double lng2 = right_top.lng_x;
		double lat2 = right_top.lat_y;
		double offsetlat = Math.abs(lat2-lat1);
		double height = toKMfromLatOffset(offsetlat);
		double mid_lat = (lat2+lat1)/2;
		//System.out.println(lat1+";"+lat2);
		double offsetlng = Math.abs(lng2 -lng1);
		double width = toKMfromLngOffset(offsetlng, mid_lat) ;
		double area = width*height;
		//String log = "height * width:"+height+"*"+width+";offset lat:"+offsetlat+","+"offset lng:"+offsetlng;
		//logger.info(log);
		return area;
	}
	
}
