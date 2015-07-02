package org.zgwu4lab.lbs.index.grid;

import java.util.ArrayList;

import org.parameters.I_constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.forMaths.MathTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.json.JsonLocation;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class GridEncoder{
	final static Logger logger = LoggerFactory.getLogger(GridEncoder.class);
	
	final static double gridgranularity = I_constant.index_grid_granularity;
	
	public static String leftbuttom(JsonLocation loc){
		String leftbuttom = leftbuttom_percent(loc);
		return leftbuttom;
	}
	
	public static String leftbuttom_percent(JsonLocation loc){
		String lng_x = MathTool.round(loc.getLng());//longitude
		String lat_y = MathTool.round(loc.getLat());//latitude
		String leftbuttom = lat_y+","+lng_x;
		return leftbuttom;
	}
	
	public static ArrayList<String> grids_kNN(JsonLocation location,int round){
		final ArrayList<String> gridarray = new ArrayList<String>();
		
		for(int r = 0;r<5;r++){
			
			if(gridarray.size()>1){
				break;
			}
		}

		return gridarray;
	}	
	
	public static ArrayList<String> grids_range(RequestUser r){
		return GridEncoder.grids_range(r.location.toJsonLocation(), r.argument.value);
	}
	
	public static ArrayList<String> grids_range(JsonLocation location, double radius_lngORlat){
		final ArrayList<String> gridarray = new ArrayList<String>();
		
		LocationCoordinate loc = new LocationCoordinate(location);
		
		//left-bottom location
		final double lb_lng_x = loc.lng_x-radius_lngORlat;
		final double lb_lat_y = loc.lat_y-radius_lngORlat;
		
		JsonLocation var_loc = new JsonLocation();
		
		double var_lng_x,var_lat_y;
		double n = radius_lngORlat*2/gridgranularity;
		logger.debug("Counting for Grid Computation(n):"+n);
		for(int j = 0;j <= n; j++){
			for(int i = 0;i<= n;i++){
				var_lng_x = lb_lng_x+i*gridgranularity;
				var_lat_y = lb_lat_y+j*gridgranularity;
				logger.debug(var_lat_y+";"+var_lng_x);
				var_loc.setLat(String.valueOf(var_lat_y));
				var_loc.setLng(String.valueOf(var_lng_x));
				String griduid = leftbuttom(var_loc);
				gridarray.add(griduid);				
			}
		}
		
		return gridarray;
	}

}
