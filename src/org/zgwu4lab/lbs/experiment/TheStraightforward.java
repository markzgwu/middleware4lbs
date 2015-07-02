package org.zgwu4lab.lbs.experiment;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.tools.forLog.AbsLog;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.framework.middletier.Middleserver;
import org.zgwu4lab.lbs.queries.rangequery.CircularRangequery4MidSrv;
import org.zgwu4lab.lbs.queries.request.RequestUser;

public class TheStraightforward extends AbsLog{
	public static Logger staticlogger = (new TheStraightforward()).logger;

	public static void test1(){
		staticlogger.info("test");
		RequestUser r = new RequestUser();
		r.location = new LocationCoordinate("40.009021,116.321625");
		r.request_id = "20081023025304";
		r.request_data = "GeoLIfe";
		final ArrayList<RequestUser> r_pool = new ArrayList<RequestUser>();
		r_pool.add(r);
		System.out.println(r_pool.size());
		Middleserver middle_server = new Middleserver(r_pool);
		double time1 = System.currentTimeMillis();
		int results_num = middle_server.execute();
		double time2 = System.currentTimeMillis();
		System.out.println(results_num);
		System.out.println("Running Time:"+(time2-time1)+"ms");		
	}
	
	public static void test2(){
		staticlogger.info("test");
		RequestUser r = new RequestUser();
		r.location = new LocationCoordinate("40.009021,116.321625");
		r.request_id = "20081023025304";
		r.request_data = "GeoLIfe";
		ArrayList<RequestUser> r_pool = new ArrayList<RequestUser>();
		r_pool.add(r);
		System.out.println(r_pool.size());
		CircularRangequery4MidSrv middle_server = new CircularRangequery4MidSrv(r);
		double time1 = System.currentTimeMillis();
		int results_num = middle_server.execute();
		double time2 = System.currentTimeMillis();
		System.out.println(results_num);
		System.out.println("Running Time:"+(time2-time1)+"ms");
		middle_server.ShowResults();
	}	
	
	public static void main(String[] args){
		test2();
	}

}
