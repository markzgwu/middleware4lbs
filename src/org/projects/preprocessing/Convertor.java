package org.projects.preprocessing;

import java.util.ArrayList;

import org.projects.datamodel.Grid;
import org.projects.datamodel.PointInEarth;
import org.projects.datamodel.PointInPlane;
import org.projects.datamodel.RectEarth;
import org.projects.datamodel.RectEnv;
import org.projects.datamodel.RectSideLength;
import org.tools.forMaths.MathTool4BigDec;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public final class Convertor {
	public static RectEarth convert(Rectangle rect){
		double x1 = rect.left_bottom.lng_x;
		double y1 = rect.left_bottom.lat_y;
		double x2 = rect.right_top.lng_x;
		double y2 = rect.right_top.lat_y;
		double xLen = MathTool4BigDec.Round12(x2-x1);
		double yLen = MathTool4BigDec.Round12(y2-y1);
		RectEarth rect1 = new RectEarth(
				new PointInEarth(x1,y1), 
				new RectSideLength(xLen,yLen));
		return rect1;
	}
	
	public static PointInEarth convert(MovingObject mo){
		return new PointInEarth(Double.parseDouble(mo.getLng()),Double.parseDouble(mo.getLat()));
	}
	
	public static String convert(MovingObject mo,RectEnv rectenv){
		final PointInEarth pEarth = convert(mo);
		final PointInPlane pPlane = rectenv.mapper.toPlanefromEarth(pEarth);
		final String cellid = rectenv.spatialSpliter.quadtreeEncoder(pPlane);
		return cellid;
	}
	
	public static String[] convert(ArrayList<MovingObject> onepath,RectEnv rectenv){
		String[] path = new String[onepath.size()];
		int i = 0;
		for(MovingObject mo:onepath){
			path[i++] = convert(mo,rectenv);
		}
		return path;
	}	
	
	public static PointInEarth convert(LocationCoordinate location){
		return new PointInEarth(location.lng_x,location.lat_y);
	}
	
	public static Grid convert(PointInPlane pPlane){
		final int i = (int)Math.floor(pPlane.getX());
		final int j = (int)Math.floor(pPlane.getY());
		return new Grid(i,j);
	}

	public static ArrayList<String> extend(ArrayList<String> nodeids,int l){
		ArrayList<String> allnodes = new ArrayList<String>();
		for(String prefix:nodeids){
			if(prefix.length()<l){
				allnodes.addAll(extend(prefix,l));
			}else{
				allnodes.add(prefix);
			}
		}
		return allnodes;		
	}
	
	public static ArrayList<String> extend(String prefix,int l){
		if(l<1){
			return null;
		}			
		//ArrayList<String> allnodes = new ArrayList<String>();
		ArrayList<String> childnodeids = null;	
		
		int a = l-prefix.length();
		if(a==1){
			childnodeids = extendByOneChar(prefix);
		}else{
			childnodeids = new ArrayList<String>();
			final ArrayList<String> prefix_extend_onechar = extendByOneChar(prefix);
			for(String tmp_prefix:prefix_extend_onechar){
				childnodeids.addAll(extend(tmp_prefix,l));
			}
		}

		return childnodeids;
	}
	
	public static ArrayList<String> extendByOneChar(final String prefix){
		final ArrayList<String> childnodeids = new ArrayList<String>();
		for(int i=0;i<4;i++){
			childnodeids.add(prefix+String.valueOf(i));
		}
		return childnodeids;
	}
	
}
