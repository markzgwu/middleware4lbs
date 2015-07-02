package measure.AuthKBA.test;

import java.io.*;
import java.sql.*;

/**
 * @author Administrator
 *
 */
public final class LoadDBforusers {
	static int limit = 5;
	public static void PrepareData(String row,int limit) throws Exception{
			String[] element_row = row.split("::");
			
			String sql = "insert users ";
			sql+="(UserID,Gender,Age,Occupation,Zipcode)";
			sql+=" VALUES(";
			sql+=element_row[0]+",'"+element_row[1]+"','"+element_row[2]+"','"+element_row[3]+"','"+element_row[4]+"'";
			sql+=")";
			
			//System.out.println(sql);
			
			statement.executeUpdate(sql);
			
			//if(message==statement.)
			
			if(element_row.length!=limit){
				System.out.println("warning!!"+row);
			}
	}
	

	public static void ReadData() throws Exception{
		  String path = "D:\\_reserach\\datasets\\movielens\\ml-1m\\users.dat";
		  FileReader reader = new FileReader(path);
		  BufferedReader br = new BufferedReader(reader);
		  String row = null;

		  int count = 0;
		  while((row = br.readLine()) != null) {
			  count ++;
			  PrepareData(row,limit);
			  //row.split(regex);
		  }
		  
		  System.out.println("the number of records:"+count);
		  br.close();
		  reader.close();
	}	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	static Statement statement = null;
	
	public static void OperatorForDB() throws Exception {

		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/movieslens1";
		String user = "root";
		String password = "12345678";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		statement = conn.createStatement();
		
		ReadData();
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}

	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			OperatorForDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success!");
	}


}
