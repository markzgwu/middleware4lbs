package org.zgwu4lab.lbs.framework.database.mysql;


import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

	public static void db() throws Exception{
		String driver = "com.mysql.jdbc.Driver";

		Class.forName(driver);

        String url = "jdbc:mysql://127.0.0.1:3306/middleware4lbs"; 
        String user = "zgwu"; 
        String password = "123456";
		
		Connection conn = DriverManager.getConnection(url, user, password);	
		
		if(!conn.isClosed()){
			System.out.println("Succeeded connecting to the Database!");
		}

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
