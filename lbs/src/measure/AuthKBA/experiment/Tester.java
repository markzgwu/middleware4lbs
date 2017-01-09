package measure.AuthKBA.experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Tester {
	ArrayList<String> egenvector=null;
	
	public void OperateData() throws Exception{
		
		ArrayList<String> vector = getvector("1");
		ShowData(vector);
	}		
	
	public void init(){
		try {
			egenvector = egenvector();
			ShowData(egenvector);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  ArrayList<String> egenvector() throws Exception{
		ArrayList<String> vector = new ArrayList<String>();
		String sql = "select * from users_num_everymovie limit 0,20";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			vector.add(rs.getString("movieid"));
		}
		return vector;
	}
	
	public String getrating(String userid,String movieid) throws Exception{
		String sql = "select * from ratings where userid="+userid+" and movieid="+movieid;
		ResultSet rs = statement.executeQuery(sql);
		String rating = "0";//never seen
		if(rs.next()){
			int r = Integer.parseInt(rs.getString("rating"));
			if(r>3){
				rating="1";//like
			}else{
				rating="-1";//don't like
			}
		}
		return rating;
	}	
	
	public ArrayList<String> getvector(String userid) throws Exception{
		ArrayList<String> vector = new ArrayList<String>();
		for(String movieid:egenvector){
			vector.add(getrating(userid,movieid));
		}
		return vector;
	}
	
	public float query(String userid,String movieid,String rating) throws Exception{
		String sql = "select count(*) from where userid="+userid+" and movieid="+movieid+" and ratings="+rating;
		ResultSet rs = statement.executeQuery(sql);
		if(rs.next()){
			
		}
		float query = 0;
		return query;
	}
	
	public void ShowData(ArrayList<String> v) throws Exception{
		
		for(String s:v){
			System.out.print(s+";");
		}
		System.out.println("");
		
	}		
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	Statement statement = null;
	
	public void OperatorForDB() throws Exception {

		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/movieslens1";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		statement = conn.createStatement();
		
		init();
		
		OperateData();
		
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
			new Tester().OperatorForDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success!");
	}

}
