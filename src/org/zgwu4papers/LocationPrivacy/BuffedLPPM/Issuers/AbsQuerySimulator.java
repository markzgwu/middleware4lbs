package org.zgwu4papers.LocationPrivacy.BuffedLPPM.Issuers;

import org.tools.forMaths.RandomTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public abstract class AbsQuerySimulator {
	public abstract void show();
	public abstract void check();
	public abstract void Generator();
	public abstract Rectangle GetRect();
	public LocationCoordinate GetALocation(){
		final Rectangle rect = GetRect();
		double random1 = RandomTool.RandomDouble();
		double random2 = RandomTool.RandomDouble();
		//System.out.println(random1+","+random2);
		LocationCoordinate left_bottom = rect.left_bottom;
		LocationCoordinate right_top = rect.right_top;
		double lng_x_diff= right_top.lng_x-left_bottom.lng_x;
		double lat_y_diff= right_top.lat_y-left_bottom.lat_y;
		
		double lng_x = lng_x_diff*random1 + left_bottom.lng_x;
		double lat_y = lat_y_diff*random2 + left_bottom.lat_y;;
		LocationCoordinate location = new LocationCoordinate(lng_x, lat_y);
		return location;
	}	
}
