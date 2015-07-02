package org.parameters;

import org.parameters.configfile.ConfigManager;
import org.zgwu4lab.lbs.datamodel.geodata.Granularity;
import org.zgwu4lab.lbs.datamodel.geodata.node.LocationCoordinate;
import org.zgwu4lab.lbs.datamodel.geodata.rect.Rectangle;

public interface I_constant {
	 int subpath_length = 2;
	 int cellid_length_max_for_recursion = 3;
	 
	 String output_graphviz = "E:\\_develop\\graphviz-2.38\\workspace\\";
	 String output_detail = ConfigManager.INSTANCE.get("output_detail");
	
	 String cache_configfile = "ehcachelocal.xml";
	
	 String db_driver = "com.mysql.jdbc.Driver";
	 String db_sp = ConfigManager.INSTANCE.get("db.mysql.db_sp");
	 String db_encoding = "useUnicode=true&characterEncoding=UTF-8";
	 String db_dbname = ConfigManager.INSTANCE.get("db.mysql.db_dbname");
	 String db_url = db_sp +db_dbname+"?"+db_encoding; 
	 String db_user = ConfigManager.INSTANCE.get("db.mysql.db_user"); 
	 String db_password = ConfigManager.INSTANCE.get("db.mysql.db_password");
	 String db_poidb_tablename = ConfigManager.INSTANCE.get("db.mysql.db_poidb_tablename");
	 String db_userdb_tablename = ConfigManager.INSTANCE.get("db.mysql.db_userdb_tablename");
	
	 Granularity downloader_granularity = new Granularity(0.05,0.05);
	// LocationCoordinate downloader_left_bottom = new LocationCoordinate(116.052995,39.680773);
	 LocationCoordinate downloader_left_bottom = new LocationCoordinate(115.052995,39.680773);
	// LocationCoordinate downloader_left_bottom = new LocationCoordinate(116.472474921728,39.885594486997);
	 LocationCoordinate downloader_right_top = new LocationCoordinate(117.138436,40.468722);
	// LocationCoordinate downloader_right_top = new LocationCoordinate(116.307679999853,39.924375142239);
	
	 Rectangle downloader_rectangle = new Rectangle(downloader_left_bottom, downloader_right_top);
	
	// LocationCoordinate downloader_left_bottom = new LocationCoordinate("39.680773,116.052995");
	// LocationCoordinate downloader_right_top = new LocationCoordinate("40.468722,117.138436");
	
	 String myak = ConfigManager.INSTANCE.get("POIdbsp.baidumaps.myak");
	 String SP_URL = ConfigManager.INSTANCE.get("POIdbsp.baidumaps.SP_URL");
	 String page_size = ConfigManager.INSTANCE.get("POIdbsp.baidumaps.page_size");	
	
	
	 String default_POIDB_filepath = ConfigManager.INSTANCE.get("default_POIDB_filepath");
	 String default_userreq_filepath = ConfigManager.INSTANCE.get("default_userreq_filepath");
	 String default_userreq_folder = ConfigManager.INSTANCE.get("default_userreq_folder");
	//public String[] keywords = {"饭店","银行","旅游","车站","学校","单位","娱乐","购物","小区","维修","医疗","服务"};
	 String[] keywords = {"单位","娱乐","购物","小区","维修","医疗","服务"};
	
	 double index_grid_granularity = 0.01;
	 
	 //int tag = 20;
	 //int tag4trim = 20;
	
	
}
