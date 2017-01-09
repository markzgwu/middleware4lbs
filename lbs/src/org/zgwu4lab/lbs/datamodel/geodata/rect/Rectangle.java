package org.zgwu4lab.lbs.datamodel.geodata.rect;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;

public class Rectangle extends AbsRect {

	public Rectangle(LocationCoordinate left_bottom,
			LocationCoordinate right_top) {
		super(left_bottom, right_top);
		// TODO Auto-generated constructor stub
	}
	
	public Rectangle(String rect_str_lbXY_rtXY) {
		super(rect_str_lbXY_rtXY);
	}

}
