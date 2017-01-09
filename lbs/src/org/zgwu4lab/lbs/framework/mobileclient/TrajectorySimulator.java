package org.zgwu4lab.lbs.framework.mobileclient;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;
import org.zgwu4lab.lbs.queries.request.RequestUser;

import com.alibaba.fastjson.JSON;

public class TrajectorySimulator extends AbsClientSimulatorJson{
	int usernum = 100;
	int pathlen = 200;
	final public String[][] locationmatrix = new String[usernum][pathlen];

	@Override
	public void check() {
		// TODO Auto-generated method stub
		
	}

	public void CreatingPath() {
		for(int i=0;i<usernum;i++){
			for(int j=0;j<pathlen;j++){
				//locationmatrix[i][j] = JSON.toJSONString(GetALocation().toJsonLocation());
				locationmatrix[i][j] = GetALocation().toLocation_yx();
			}
		}
	}
	
	@Override
	public void Generator() {
		for(int i=0;i<usernum;i++){
			for(int j=0;j<pathlen;j++){
				RequestUser u = new RequestUser();
				u.username = String.valueOf(i);
				u.time = String.valueOf(j);
				u.location = new LocationCoordinate(locationmatrix[i][j]);
				String json = JSON.toJSONString(u);
				QueryRequests.add(json);
			}
		}
	}

	@Override
	public Rectangle GetRect() {
		final Rectangle rect = new Rectangle("116.223745,39.842732,116.713573,40.149616");
		return rect;
	}
	
	@Override
	public void show() {
		for(int i=0;i<usernum;i++){
			for(int j=0;j<pathlen;j++){
				//locationmatrix[i][j] = JSON.toJSONString(GetALocation().toJsonLocation());
				System.out.println(locationmatrix[i][j]);
			}
		}
		System.out.println();
	}	
	
	public static void main(String[] args) {
		TrajectorySimulator sim  = new TrajectorySimulator();
		sim.Generator();
		sim.show();
	}
}
