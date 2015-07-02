package measure.AuthKBA.experiments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import measure.AuthKBA.experiment.BAnaivebayesByString;
import measure.AuthKBA.experiment.KBAnaivebayes;

public final class DoExtractDataSlimProfile {
	boolean logging = false;

	ArrayList<Integer> users=new ArrayList<Integer>();
	ArrayList<String> titles=new ArrayList<String>();
	ArrayList<Integer> movies=new ArrayList<Integer>();
		
	int claimant;
	
	String[][] knowledge_data = null;
	
	ParameterPackage mypp = null;
	
	public DoExtractDataSlimProfile(ParameterPackage input_pp){
		claimant = input_pp.claimant;
		mypp = input_pp;
	}
	
	public void ShowData(int[] data){
		for(int s:data){
			System.out.print(s+";");
		}
		System.out.println("");
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
	
	public void ShowData(ArrayList<Integer> v) throws Exception{
		
		for(Integer s:v){
			System.out.print(s+";");
		}
		System.out.println("");
		
	}		
	
	public void print_knowledge_data(){
		
		for(String title:titles){
			System.out.print(title+";");
		}
		System.out.println("\n------------------------------");
		
		for(String[] row:knowledge_data){
			String knownledge_row="";
			for(String knowledge:row){
				knownledge_row+=knowledge+";";
			}
			System.out.println(knownledge_row);
		}
		
	}
	
	public void LoadUsers() throws Exception{
		{
			int start_column= mypp.start_column;
			int profile_len = mypp.profile_len;//0表示不选择porfile作为数据源
			int movies_len = mypp.movies_len;			
			int knowledge_len = profile_len+movies_len;
			int end_column=start_column+profile_len;
			
			//int start_column_movies=profile_len;
			//int end_column_movies=start_column_movies+movies_len;			
			
			String sql = "select * from users";
			ResultSet rs = statement.executeQuery(sql);
			rs.last();
			int row_number = rs.getRow();
			knowledge_data = new String[row_number][knowledge_len];
			rs.beforeFirst();
			
			ResultSetMetaData rsm = rs.getMetaData();
			for(int t=start_column;t<end_column;t++){
				titles.add(rsm.getColumnName(t));
			}
			
			while(rs.next()){
				int userid = rs.getInt(1);
				users.add(userid);
				int userindex=users.lastIndexOf(userid);
				int j=0;
				for(int t=start_column;t<end_column;t++){
					knowledge_data[userindex][j]=rs.getString(t);
					j++;
				}
			}
		}
		
	}	
	
	public void SelectMovies(int claimant) throws Exception{
		{
			//int start_column= mypp.start_column;
			//int profile_len = mypp.profile_len;//0表示不选择porfile作为数据源
			int movies_len = mypp.movies_len;			
			
			String sql = "select movieid from ratings where userid = "+claimant+" limit 0,"+movies_len;
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()){
				movies.add(rs.getInt("movieid"));
			}
			
			for(Integer movieid:movies){
				titles.add(String.valueOf(movieid));
			}
			
		}
	}
	
	public void Loadmovies() throws Exception{
		//int start_column= mypp.start_column;
		int profile_len = mypp.profile_len;//0表示不选择porfile作为数据源
		//int movies_len = mypp.movies_len;		
		//int knowledge_len = profile_len+movies_len;
		//int end_column=start_column+profile_len;
		int start_column_movies=profile_len;
		//int end_column_movies=start_column_movies+movies_len;		
		
		String sql = "select * from ratings where";
		
		int count = 0;
		for(Integer movieid:movies){
			sql+=" movieid="+movieid;
			count++;
			if(count!=movies.size()){
				sql+=" or";
			}
		}
		
		if(logging){
			System.out.println(sql);
		}
		
		
		ResultSet rs = statement.executeQuery(sql);
		
		while(rs.next()){
			
			int userid = rs.getInt("userid");
			int movieindex = start_column_movies + movies.lastIndexOf(rs.getInt("movieid"));
			//int r = slimrating(Integer.parseInt(rs.getString("rating")));
			
			int userindex = users.lastIndexOf(userid);
			knowledge_data[userindex][movieindex] = rs.getString("rating");
		}

	}	
	
	public void OperateData() throws Exception{
		//int start_column= mypp.start_column;
		//int profile_len = mypp.profile_len;//0表示不选择porfile作为数据源
		int movies_len = mypp.movies_len;		
		
		SelectMovies(claimant);		
		LoadUsers();
		if(movies_len>0){
			Loadmovies();
		}
		
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	Statement statement = null;
	
	public void OperatorForDB() throws Exception {

		GetDataFromDB();
		
		new BAnaivebayesByString(knowledge_data,knowledge_data[claimant]).worker2();
		
		System.out.println();
		
	}	
	
	
	private void GetDataFromDB() throws Exception {

		// TODO Auto-generated method stub
		String driver = ParametersDb.driver;
		String url = ParametersDb.url;
		String user = ParametersDb.user;
		String password = ParametersDb.password;

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed()){
			if(KBAnaivebayes.log){
				System.out.println("Succeeded in connecting to the Database!");
			}
		}
		
		statement = conn.createStatement();
		
		OperateData();
		
		conn.close();
		if(conn.isClosed()){
			if(KBAnaivebayes.log){
				System.out.println("Succeeded in disconnecting to the Database!");
			}
		}
		
		System.out.println("Data Has Been Loaded!");
		
		//System.out.println(knowledge_data[clamint]);
		
		//System.out.println(knowledge_data);
		if(logging){
			print_knowledge_data();
		}
		
		
		
		System.out.println();
		
	}	
	
	public DataPackage GetDataPackage(){
		try {
			GetDataFromDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataPackage mydp = new DataPackage();
		mydp.setInput_claimant_index_in_data(claimant);
		mydp.setInput_knownledge_data(knowledge_data);
		mydp.setInput_evdience(knowledge_data[claimant]);
		return mydp;
	}

}
