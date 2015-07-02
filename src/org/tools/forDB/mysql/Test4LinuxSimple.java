package org.tools.forDB.mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.parameters.I_constant;
import org.projects.measurement.Measurement;

public class Test4LinuxSimple {

	final static String driver = I_constant.db_driver;
	final static String db_sp = I_constant.db_sp;
	final static String encoding = "useUnicode=true&characterEncoding=UTF-8";
	final static String dbname = I_constant.db_dbname;
	final static String url = db_sp +dbname+"?"+encoding; 
    final static String user = I_constant.db_user; 
    final static String password = I_constant.db_password;
    final static String tablename = dbname+"."+I_constant.db_poidb_tablename;

    public static Connection connect() throws Exception{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded connecting to the Database!");
		}		
		return conn;
    }
    
    public static void close(Connection conn) throws Exception{
		conn.close();
		conn = null;
    }
    
	public static void db(String uid,Connection conn) throws Exception{
		//System.out.println(url+"\n"+user+";"+password);
		
		long startTime=System.nanoTime();   //获取开始时间 
		readdb(conn,uid);
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		System.out.println(Measurement.toMSfromNS(elapsedTime)); 
		


	}
	
	public static void readdb(Connection conn,String uid) throws Exception{
        String sql = "select data from "+tablename+" where uid='"+uid+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int count = 0;
        while(rs.next()){
        	//System.out.println(rs.getString("data"));
        	count++;
        }
        //System.out.println(count+" Data Retrieval!");
	}
	
	public static void worker(Connection conn){
		String uid = "123dbaffdfd667a7233bf210";
		//String uid = "123";
		try {
			db(uid,conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Connection conn = connect();
		
		long startTime=System.nanoTime();   //获取开始时间 
		worker(conn);
		long endTime=System.nanoTime(); //获取结束时间  
		long elapsedTime = (endTime-startTime);
		System.out.println(Measurement.toMSfromNS(elapsedTime)); 
		
		close(conn);
	}
	
}
