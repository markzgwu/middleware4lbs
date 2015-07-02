package org.zgwu4lab.lbs.framework.database.mysql;


import java.io.*;
import java.sql.*;

import org.assistants.dbsp.jsonprocessor.Fastjson;
import org.parameters.I_constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;

public class Test4Linux {
	private static final Logger logger = LoggerFactory.getLogger(Test4Linux.class);

	final static String driver = I_constant.db_driver;
	final static String db_sp = I_constant.db_sp;
	final static String encoding = "useUnicode=true&characterEncoding=UTF-8";
	final static String dbname = I_constant.db_dbname;
	final static String url = db_sp +dbname+"?"+encoding; 
    final static String user = I_constant.db_user; 
    final static String password = I_constant.db_password;
    final static String tablename = dbname+"."+I_constant.db_poidb_tablename;
    
	public static void db() throws Exception{
		System.out.println(url);
		System.out.println(user+";"+password);
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);	
		ParsePOIs_ReadJsonFileByLines(I_constant.default_POIDB_filepath,conn);
		if(!conn.isClosed()){
			System.out.println("Succeeded connecting to the Database!");
		}

	}
	
	public static PreparedStatement optdb(Connection conn) throws Exception{
        String sql = "insert into "+tablename+" (uid, data) values (?, ?)"; 
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps;
	}
	
	public static void ParsePOIs_ReadJsonFileByLines(String filepath,Connection conn) throws Exception{
		PreparedStatement ps = optdb(conn);

		final File file = new File(filepath);
        final BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                POIentry onepoi = Fastjson.parsePOIfromJson(tempString);
                add_db_dup_key(onepoi,ps);
                logger.debug("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
            logger.debug("Successfully loading the POI store file:"+filepath);
	}
	
	public static void add_db_dup_key(POIentry onepoi,PreparedStatement ps){
		try {
			add_db(onepoi,ps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(onepoi.getUid()+"exists here! Duplicate Entry!");
			//e.printStackTrace();
		}
	}	
	
	public static void add_db(POIentry onepoi,PreparedStatement ps) throws Exception{
        ps.setString(1, onepoi.getUid()); 
        ps.setString(2, onepoi.getData());
        ps.executeUpdate();
	}
	
	public static void main(String[] args) {

		try {
			db();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
