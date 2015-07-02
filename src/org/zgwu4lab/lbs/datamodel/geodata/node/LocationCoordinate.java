package org.zgwu4lab.lbs.datamodel.geodata.node;

import java.io.Serializable;
import java.math.BigDecimal;

import org.projects.datamodel.PointInEarth;
import org.tools.forMaths.MathTool;
import org.tools.forString.StringTool;
import org.zgwu4lab.lbs.datamodel.json.JsonLocation;

public final class LocationCoordinate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public double lng_x=0;//X ¶«¾­ longitude
	public double lat_y=0;//Y ±±Î³  latitude
	
	public LocationCoordinate(){
	}	
	
	public final double getLng_x() {
		return lng_x;
	}

	public final void setLng_x(double lng_x) {
		this.lng_x = lng_x;
	}

	public final double getLat_y() {
		return lat_y;
	}

	public final void setLat_y(double lat_y) {
		this.lat_y = lat_y;
	}



	public LocationCoordinate(double lng_x,double lat_y){
		this.lng_x = lng_x;
		this.lat_y = lat_y;
	}
	
	public LocationCoordinate(String lat_y_lng_x){
		fromLocation_yx(lat_y_lng_x);
	}
	
	public LocationCoordinate(JsonLocation location){
		String lat_y_lng_x = location.getLat()+","+location.getLng();
		fromLocation_yx(lat_y_lng_x);
	}	
	
	public String toString(){
		return lng_x+","+lat_y;
	}
	
	public JsonLocation toJsonLocation(){
		JsonLocation loc = new JsonLocation();
		loc.setLat(String.valueOf(lat_y));
		loc.setLng(String.valueOf(lng_x));
		return loc;
	}
	
	public void fromLocation_yx(final String lat_y_lng_x){
		//System.out.println(lat_y_lng_x);
		String[] strarray = StringTool.split(lat_y_lng_x,",");
		final double d_lat_Y = Double.parseDouble(strarray[0]);
		final double d_lng_X = Double.parseDouble(strarray[1]);
		this.lat_y = d_lat_Y;
		this.lng_x = d_lng_X;
	}	
	
	public PointInEarth toPointInEarth(){
		return new PointInEarth(lng_x,lat_y);
	}
	
	public String toLocation_yx(){
		double d_lat_Y = MathTool.round(lat_y,6,BigDecimal.ROUND_HALF_DOWN);
		double d_lng_X = MathTool.round(lng_x,6,BigDecimal.ROUND_HALF_DOWN);
		String location=d_lat_Y+","+d_lng_X;
		return location;
	}	
}
