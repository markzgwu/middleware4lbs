package measure.AuthKBA.experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public final class DoExtractData2 {

	ArrayList<Integer> movies=null;
	ArrayList<Integer> users=null;
	int[][] ratings = null;
	//int clamint = 5;
	
	public synchronized final ArrayList<Integer> getMovies() {
		return movies;
	}

	public synchronized final void setMovies(ArrayList<Integer> movies) {
		this.movies = movies;
	}

	public synchronized final ArrayList<Integer> getUsers() {
		return users;
	}

	public synchronized final void setUsers(ArrayList<Integer> users) {
		this.users = users;
	}

	public void OperateData(int clamint) throws Exception{
		
		//ArrayList<Integer> vector = getvector("1");
		//ShowData(vector);
		getrating();
		System.out.println("Dataset ready!");
		
		int userindex = users.lastIndexOf(clamint);
		System.out.println("user["+userindex+"]="+clamint);
		KBAnaivebayes task = new KBAnaivebayes(ratings,ratings[userindex]);
		double ln = task.getlikenessrate();
		linknessrate = ln;
		
	}		
	
	public void initusers(){
		
		try {
			users = egenusers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init(int clamint){
		try {
			movies = selectedmovies_half_half(clamint);
			ShowData(movies);
			//users = egenusers();
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
	
	public boolean checkHadMovies(int movieid,ArrayList<Integer> hadmovies){
		boolean b = false;
		for(Integer hadmovie:hadmovies){
			if(hadmovie==movieid){
				b = true;
				break;
			}
		}
		return b;
	}
	
	public  ArrayList<Integer> selectedmovies_half_half(int claimantid) throws Exception{
		
		ArrayList<Integer> notseenmovies = new ArrayList<Integer>();
		
		ArrayList<Integer> selectedmovies = new ArrayList<Integer>();
		//String extentmovies = "";
		{
		//String sql = "select movieid from ratings where userid="+claimantid+" limit 0,10";
		String sql = "select * from ratings where userid="+claimantid;
		System.out.println(sql);
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			String movieid = rs.getString("movieid");
			selectedmovies.add(Integer.parseInt(movieid));
			//System.out.println(rs.getString("movieid"));
			//extentmovies += " or movieid!="+movieid;
		}
		rs.close();
		}
		
		//System.out.println(extentmovies);
		
		{
		//String sql1 = "select movieid,count(userid) as countusers from ratings where userid!="+claimantid+" group by movieid order by countusers desc limit 0,10";
		//String sql = "select * from ratings where userid="+claimantid;
		String sql1 = "select movieid,count(userid) as countusers from ratings where userid!="+claimantid+" group by movieid order by countusers desc";
		//String sql1 = "select movieid from movies limit 0,20";
		System.out.println(sql1);
		ResultSet rs1 = statement.executeQuery(sql1);
		int limitcount = 10;
		while(rs1.next()){
			int movieid = Integer.parseInt(rs1.getString("movieid"));
			boolean b = !checkHadMovies(movieid,selectedmovies);
			if(b){
				notseenmovies.add(movieid);
			}
			if(notseenmovies.size()==limitcount) break;
			//System.out.println(rs1.getString("movieid"));
		}
		rs1.close();
		}
		
		selectedmovies.addAll(notseenmovies);
		
		return selectedmovies;
	}	
	
	public  ArrayList<Integer> selectedmovies(int claimantid) throws Exception{
		ArrayList<Integer> vector = new ArrayList<Integer>();
		//String sql = "select * from ratings where userid="+claimantid+" limit 0,20";
		String sql = "select movieid from ratings where userid="+claimantid;
		System.out.println(sql);
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
		System.out.println(sql);
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
			//System.out.print("ratings["+userindex+"]["+movieindex+"]="+r);
		}

	}	
	
	public int slimrating(int r){
		int rating=0;
		if(r>3){
			rating=2;//like
		}else if(r>0){
			rating=1;//don't like
		}		
		return rating;
	}
	
	public void ShowData(ArrayList<Integer> v) throws Exception{
		
		for(Integer s:v){
			System.out.print(s+";");
		}
		System.out.println("");
		
	}		
	
	public void InitusersForDB() throws Exception {

		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/movieslens_slim";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		statement = conn.createStatement();
		
		initusers();
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}

	}		
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	Statement statement = null;
		
	public void OperatorForDB(int claimint) throws Exception {

		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/movieslens_slim";
		String user = "root";
		String password = "testtest";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			System.out.println("Succeeded in connecting to the Database!");
		}
		
		statement = conn.createStatement();
		
		init(claimint);
		
		OperateData(claimint);
		
		conn.close();
		if(conn.isClosed()){
			System.out.println("Succeeded in disconnecting to the Database!");
		}

	}	
	
	
	double linknessrate=0;
	
	public double getLN(){
		return linknessrate;
	}
	
	public void doworker(){
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DoExtractData2 op = new DoExtractData2();
		int usernum=2;
		int success=0;
		int count=0;
		try {
			op.InitusersForDB();
			ArrayList<Integer> users = op.getUsers();
			
			for(int claimint:users){
				op.OperatorForDB(claimint);
				count++;
				if(count>usernum){
					break;
				}
				if(op.getLN()>1){
					success++;
				}
			}
			System.out.println("ARR:"+(success/usernum));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success!");
	}

}
