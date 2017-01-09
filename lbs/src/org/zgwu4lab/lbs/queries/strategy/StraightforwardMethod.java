package org.zgwu4lab.lbs.queries.strategy;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.tools.forLog.AbsLog;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.framework.middletier.Middleserver;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public class StraightforwardMethod extends AbsLog{
	public static Logger staticlogger = (new StraightforwardMethod()).logger;

	public static void main(String[] args){
		staticlogger.info("test");
		RequestUser r = new RequestUser();
		r.location = new LocationCoordinate("40.009021,116.321625");
		r.request_id = "20081023025304";
		r.request_data = "GeoLIfe";
		ArrayList<RequestUser> r_pool = new ArrayList<RequestUser>();
		r_pool.add(r);
		System.out.println(r_pool.size());
		Middleserver middle_server = new Middleserver(r_pool);
		int results_num = middle_server.execute();
		System.out.println(results_num);
	}

}
