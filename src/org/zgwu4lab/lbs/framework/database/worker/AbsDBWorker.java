package org.zgwu4lab.lbs.framework.database.worker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.parameters.I_constant;
import org.tools.forLog.AbsLog;

public abstract class AbsDBWorker extends AbsLog{
	public final String driver = I_constant.db_driver;
	public final String db_sp = I_constant.db_sp;
	public final String encoding = "useUnicode=true&characterEncoding=UTF-8";
	public final String dbname = I_constant.db_dbname;
	public final String url = db_sp +dbname+"?"+encoding; 
	public final String user = I_constant.db_user; 
	public final String password = I_constant.db_password;
	
	protected Connection conn = null;
	protected PreparedStatement ps = null;
	
	public void init() throws Exception{
		System.out.println(url);
		System.out.println(user+";"+password);
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);	
		if(!conn.isClosed()){
			logger.info("Succeeded connecting to the Database!");
		}
	}
	
	public String sharding = null ;
	private String history_sharding = null;
	public void init_ps() throws Exception{
		boolean b = (!sharding.equals(history_sharding));
		if(ps==null){
			ps = optdb();
		}else{
			if(b){
				ps.close();
				ps = null;
				ps = optdb();
			}
		}

		history_sharding = sharding;
	}
	
	protected abstract String SQLTemplate();
	
	protected PreparedStatement optdb() throws Exception{
        String sql = SQLTemplate();
        final PreparedStatement ps = conn.prepareStatement(sql);
        System.out.println("NEW prepareStatement!"+sql);
        sql = null;
        return ps;
	}
	
	public void add_db_dup_key(String[] fields){
		try {
			add_db(fields);
		} catch (Exception e) {
			System.out.print("Duplicate Entry:"+fields);
			//e.printStackTrace();
		}
	}
	
	private void add_db(String[] dbfields) throws Exception{
		//logger.info("dbfields.length="+dbfields.length);
		//logger.debug(StringTool.ShowStringArray(dbfields));
		
		for(int i=0;i<dbfields.length;i++){
	        ps.setString(i+1, dbfields[i]); 
		}
        ps.executeUpdate();
	}
	
	public void add_db_dup_key_batch(String[] fields){
		try {
			add_db_batch(fields);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void add_db_batch(String[] dbfields) throws Exception{
		for(int i=0;i<dbfields.length;i++){
	        ps.setString(i+1, dbfields[i]); 
		}
        ps.addBatch();
	}
	
	public void Batch(){
		try {
			ps.executeBatch();
			ps.clearBatch();
			System.out.print("executeBatch success!");
		} catch (SQLException e) {
			System.out.print("Duplicate Entry!");
			//e.printStackTrace();
		}
	}
	
	public void close_ps(){
		try {
			//ps.closeOnCompletion();
			//conn.commit();
			ps.close();
			ps = null;
			//conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			//ps.closeOnCompletion();
			//conn.commit();
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
