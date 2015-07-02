package org.zgwu4lab.lbs.datamodel.geodata.measure;

public class MapDistanceA1 extends AbsMapDistance{ 
    public double getDistance(double lat1, double lng1, double lat2, double lng2) { 
    	double PI = Math.PI;
    	double R = this.EARTH_RADIUS;
        double x, y, distance;
        x = (lng2 - lng1)*PI*R*Math.cos(((lat1+lat2)/2)*PI/180)/180;
        y = (lat2 - lat1)*PI*R/180;
        distance = Math.hypot(x, y);
        return distance;
    }
}
