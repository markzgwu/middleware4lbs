package org.zgwu4lab.lbsn.mechanism;

import java.util.ArrayList;

import net.minidev.json.JSONArray;

import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public final class GetAllUsers {

	public static String run(String rectregion){
		final Rectangle rect = new Rectangle(rectregion);
		final ArrayList<RequestUser> QueryRequests = new ArrayList<RequestUser>();
		final ArrayList<RequestUser> FilterRequests = new ArrayList<RequestUser>();
		for(RequestUser u:QueryRequests){
			System.out.println(u.location.toLocation_yx()+";"+rect.isWithinTheRect(u.location));
			if(rect.isWithinTheRect(u.location)){
				FilterRequests.add(u);
			}
		}
		String r = JSONArray.toJSONString(FilterRequests);
		return r;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
