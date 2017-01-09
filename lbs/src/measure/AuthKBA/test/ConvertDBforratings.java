package measure.AuthKBA.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public final class ConvertDBforratings {
	static String server = "192.168.56.101";
	static int limit = 4;
	public static void PrepareData(String row,int limit) throws Exception{
			String[] element_row = row.split("::");
			
			String sql = "insert ratings ";
			sql+="(UserID,MovieID,Rating,Timestamp)";
			sql+=" VALUES(";
			sql+=element_row[0]+",'"+element_row[1]+"','"+element_row[2]+"','"+element_row[3]+"'";
			sql+=")";
			
			//System.out.println(sql);
			
			statement.executeUpdate(sql);
			
			//if(message==statement.)
			
			if(element_row.length!=limit){
				System.out.println("warning!!"+row);
			}
	}
	
	public static ArrayList<String> ReadForDB(String userid) throws Exception {

		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+server+":3306/movieslens1";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		Statement statement1 = conn.createStatement();
		
		String sql = "select * from ratings where userid = "+userid;
		ResultSet rs = statement1.executeQuery(sql);
		ArrayList<String> results = new ArrayList<String>();
		while(rs.next()){
			String s = rs.getString(1)+"::"+rs.getString(2)+"::"+rs.getString(3)+"::"+rs.getString(4);
			results.add(s);
		}
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}
		
		return results;
	}	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	static Statement statement = null;
	
	public static void OperatorForDB() throws Exception {
		
		
		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+server+":3306/movieslens_slim";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		statement = conn.createStatement();
		
		for(String userid:UsersForDB()){
			

			ArrayList<String> results = ReadForDB(userid);
			
			for(String row : results){
				
				PrepareData(row,limit);
				
			}
		
		}
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}

	}	
	
	public static ArrayList<String> UsersForDB() throws Exception {
		
		ArrayList<String> results = new ArrayList<String>();
		
		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+server+":3306/movieslens_slim";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		Statement statement2 = conn.createStatement();
		String sql = "select userid from users";
		ResultSet rs = statement2.executeQuery(sql);
		
		while(rs.next()){
			results.add(rs.getString(1));
		}
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Getting "+results.size()+" users!");
		}
		
		return results;
	}	
	
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
