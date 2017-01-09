package org.projects.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.parameters.I_constant;

public final class WorkerMysql {
	final static String driver = I_constant.db_driver;
	final static String db_sp = I_constant.db_sp;
	final static String encoding = "useUnicode=true&characterEncoding=UTF-8";
	final static String dbname = I_constant.db_dbname;
	final static String url = db_sp +dbname+"?"+encoding; 
    final static String user = I_constant.db_user; 
    final static String password = I_constant.db_password;
    final static String tablename = dbname+"."+I_constant.db_poidb_tablename;
    
    Connection conn = null;
    
    public void init(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()){
				System.out.println("Succeeded connecting to the Database!");
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    
    
    public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = null;
    }    
    
	String process(String uid) throws Exception{
		Statement stmt = null;
        String sql = "select data from "+tablename+" where uid='"+uid+"'";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int count = 0;
        String result = null;
        while(rs.next()){
        	result = (rs.getString("data"));
        	count++;
        }
        System.out.println(count+" Data Retrieval!");
        return result;
	}
    
	public String read(String uid){
        String result = null;
        try {
			result = process(uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
	}

}
