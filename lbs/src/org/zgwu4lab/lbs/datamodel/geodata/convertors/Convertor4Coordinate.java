package org.zgwu4lab.lbs.datamodel.geodata.convertors;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;

public final class Convertor4Coordinate {
	final int interval = 100000;
	final LocationCoordinate orginpoint;
	Convertor4Coordinate(final LocationCoordinate theOriginPoint){
		orginpoint = theOriginPoint;
	}
	
	public Point obtainPoint(final LocationCoordinate locationInMaps){
		final Point pointInXYPlane = new Point();
		pointInXYPlane.x = (locationInMaps.lng_x - orginpoint.lng_x) * interval;
		pointInXYPlane.y = (locationInMaps.lat_y - orginpoint.lat_y) * interval;
		return pointInXYPlane;
	}

}
