package org.zgwu4lab.lbs.framework.mobileclient;

import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;
import org.zgwu4lab.lbs.queries.request.QueryArgument;
import org.zgwu4lab.lbs.queries.request.RequestUser;

import com.alibaba.fastjson.JSON;

public class ClientSimulatorJson extends AbsClientSimulatorJson {

	public final int NodeCount = 100;
	@Override
	public Rectangle GetRect() {
		final Rectangle rect = new Rectangle("116.223745,39.842732,116.713573,40.149616");
		return rect;
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
			String json = JSON.toJSONString(req);
			QueryRequests.add(json);
		}
	}
	
	public void check() {
		final Rectangle rect = GetRect();
		for(String json:QueryRequests){
			RequestUser u = RequestUser.fromJson(json);
			System.out.println(u.location.toLocation_yx()+";"+rect.isWithinTheRect(u.location));
		}
	}

}
