package org.zgwu4lab.lbs.queries.request;

import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;


public abstract class AbsRequestUser {
	public String request_id;
	public String username;
	public String time;
	public LocationCoordinate location;
	public String request_data;
}
