package measure.AuthKBA.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public final class ConvertDBforusers {
	static String server = "192.168.56.101";
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
	
	public static ArrayList<String> ReadForDB() throws Exception {
		ArrayList<String> results = new ArrayList<String>();
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
		
		for(String userid:UsersForDB()){
		
			String sql = "select * from users where userid ="+userid;
			ResultSet rs = statement1.executeQuery(sql);
			if(rs.next()){
				String s = rs.getString(1)+"::"+rs.getString(2)+"::"+rs.getString(3)+"::"+rs.getString(4)+"::"+rs.getString(5);
				results.add(s);
			}
		
		}
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}
		
		return results;
	}	
	
	public static ArrayList<String> UsersForDB() throws Exception {
		
		ArrayList<String> results = new ArrayList<String>();
		
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
		
		Statement statement2 = conn.createStatement();
		String sql = "select userid from users limit 0,500";
		ResultSet rs = statement2.executeQuery(sql);
		
		while(rs.next()){
			results.add(rs.getString(1));
		}
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Getting 500 users!");
		}
		
		return results;
	}	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	static Statement statement = null;
	
	public static void OperatorForDB() throws Exception {
		
		ArrayList<String> results = ReadForDB();
		
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
		
		for(String row : results){
			
			PrepareData(row,limit);
			
		}
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}

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
