package measure.AuthKBA.experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public final class DoExtractDataSlim {

	ArrayList<Integer> movies=null;
	ArrayList<Integer> users=null;

	int[][] ratings = null;
	int clamint;
	
	public synchronized final int[][] getRatings() {
		return ratings;
	}


	public DoExtractDataSlim(int userid){
		clamint = userid;
	}
	
	public void OperateData() throws Exception{
		
		//ArrayList<Integer> vector = getvector("1");
		//ShowData(vector);
		getrating();
		System.out.println("Dataset ready!");
		
		int userindex = users.lastIndexOf(clamint);
		if(KBAnaivebayes.log){
			System.out.println("user["+userindex+"]="+clamint);
			System.out.println("---------------------------------------");			
		}

		new KBAnaivebayes(ratings,ratings[userindex]).worker2();
		ShowData(ratings[userindex]);
		if(KBAnaivebayes.log){		
			System.out.println("---------------------------------------");
		}
		
	}		
	
	public void ShowData(int[] data){
		for(int s:data){
			System.out.print(s+";");
		}
		System.out.println("");
	}
	
	public void init(){
		try {
			movies = selectedmovies(clamint);
			ShowData(movies);
			users = egenusers();
			//ShowData(users);
			int m = users.size();
			int n = movies.size();
			ratings = new int[m][n];
			
			for(int i=0;i<m;i++){
				for(int j=0;j<n;j++){
					ratings[i][j]=0;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  ArrayList<Integer> selectedmovies1() throws Exception{
		ArrayList<Integer> vector = new ArrayList<Integer>();
		String sql = "select movieid from ratings group by movieid";
		//String sql = "select * from ratings where userid="+claimantid;
		System.out.println(sql);
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			vector.add(Integer.parseInt(rs.getString("movieid")));
		}
		
		return vector;
	}	
	
	public  ArrayList<Integer> selectedmovies(int claimantid) throws Exception{
		ArrayList<Integer> vector = new ArrayList<Integer>();
		String sql = "select * from ratings where userid="+claimantid+" limit 0,10";
		//String sql = "select * from ratings where userid="+claimantid;
		
		if(KBAnaivebayes.log){
			System.out.println(sql);
		}
		
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			vector.add(Integer.parseInt(rs.getString("movieid")));
		}
		return vector;
	}
	
	public  ArrayList<Integer> egenusers() throws Exception{
		ArrayList<Integer> vector = new ArrayList<Integer>();
		//String sql = "select * from ratings where userid="+userid+" limit 0,20";
		
		String sql = "select userid from users order by userid";
		if(KBAnaivebayes.log){
			System.out.println(sql);
		}
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			vector.add(rs.getInt("userid"));
		}
		return vector;
	}	
	
	
	public void getrating() throws Exception{
		for(int movieid:movies){
			getrating(movieid);
		}
	}
	
	public void getrating(int movieid) throws Exception{
		String sql = "select * from ratings where movieid="+movieid;
		ResultSet rs = statement.executeQuery(sql);
		int movieindex = movies.lastIndexOf(movieid);
		while(rs.next()){
			
			int userid = rs.getInt("userid");
			int r = slimrating(Integer.parseInt(rs.getString("rating")));
			
			int userindex = users.lastIndexOf(userid);
			
			ratings[userindex][movieindex] = r;
		}

	}	
	
	public int slimrating(int r){
		/*
		int rating=0;
		if(r>3){
			rating=2;//like
		}else if(r>0){
			rating=1;//don't like
		}
		
		return rating;
		*/
		return r;
	}
	
	public float query(String userid,String movieid,String rating) throws Exception{
		String sql = "select count(*) from where userid="+userid+" and movieid="+movieid+" and ratings="+rating;
		ResultSet rs = statement.executeQuery(sql);
		if(rs.next()){
			
		}
		float query = 0;
		return query;
	}
	
	public void ShowData(ArrayList<Integer> v) throws Exception{
		
		for(Integer s:v){
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
		String url = "jdbc:mysql://127.0.0.1:3306/movieslens_slim";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			if(KBAnaivebayes.log){
				System.out.println("Succeeded in connecting to the Database!");
			}
		}
		
		statement = conn.createStatement();
		
		init();
		
		OperateData();
		
		conn.close();
		if(conn.isClosed()){
			if(KBAnaivebayes.log){
				System.out.println("Succeeded in disconnecting to the Database!");
			}
		}
		
		System.out.println();
		
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			for(int i=1;i<11;i++){
				new DoExtractDataSlim(i).OperatorForDB();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success!");
	}

}
