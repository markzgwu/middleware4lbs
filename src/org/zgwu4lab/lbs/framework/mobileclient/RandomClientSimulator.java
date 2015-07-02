package org.zgwu4lab.lbs.framework.mobileclient;

import java.util.HashSet;

import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class RandomClientSimulator extends AbsClientSimulator{
	final public HashSet<RequestUser> QueryRequests = new HashSet<RequestUser>();
	public final int NodeCount = 100;
	@Override
	public Rectangle GetRect() {
		final Rectangle rect = new Rectangle("116.223745,39.842732,116.713573,40.149616");
		return rect;
	}	
	
	public static void main(String[] args) {
		RandomClientSimulator sim = new RandomClientSimulator();
		sim.Generator();
		sim.check();
	}

	public void check() {
		final Rectangle rect = GetRect();
		for(RequestUser u:QueryRequests){
			System.out.println(u.location.toLocation_yx()+";"+rect.isWithinTheRect(u.location));
		}

	}
	
	@Override
	public void Generator() {
		for(int i = 0;i<NodeCount;i++){
			RequestUser req = new RequestUser();
			req.argument = new QueryArgument("radius_degree" , 0.01);
			req.request_data = "beijing";
			req.request_id = String.valueOf(i);
			req.username = String.valueOf(i);
			req.location = GetALocation();
			QueryRequests.add(req);	
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


}
