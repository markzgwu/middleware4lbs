package org.zgwu4lab.lbs.datamodel.geodata.rect;

import java.util.ArrayList;

import org.assistants.dbsp.baidumaps.TaskContext;
import org.zgwu4lab.lbs.datamodel.geodata.Granularity;
import org.zgwu4lab.lbs.datamodel.geodata.MapsTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;

public class RectBounds extends AbsRect{
	public Granularity granularity = null;
	
	public RectBounds(LocationCoordinate left_bottom,LocationCoordinate right_top){
		super(left_bottom,right_top);
		this.granularity = new Granularity();
	}
	
	public RectBounds(LocationCoordinate left_bottom,LocationCoordinate right_top,Granularity granularity){
		super(left_bottom,right_top);
		this.granularity = granularity;
	}
	
	public RectBounds(Granularity granularity){
		super(null,null);
		this.granularity = granularity;
	}
	
	public void Load_TC(TaskContext tc){
		Load_lbYX_rtYX(tc.bounds_lbYX_rtYX);
	}

	public void info(){
		System.out.println("(granularity) Area of Grid:"+granularity.AREA()+" km2");		
		System.out.println("lbYX,rtYX:"+left_bottom.toLocation_yx()+","+right_top.toLocation_yx()+";Area:"+AREA()+" km2");
	}	
	
	public ArrayList<String> getRectList(){
		return getRectList(granularity);
	}
	
	private ArrayList<String> getRectList(Granularity g){
		ArrayList<String> rect_list = MapsTool.SplitRectBounds(this,g);
		System.out.println("NUM of GRIDs:"+rect_list.size());
		return rect_list;
	}

	
	
}
