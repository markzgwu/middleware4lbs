package org.assistants.userdb;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.framework.database.worker.AbsDBWorker;

public final class Sharding_UserDBWorkerV2 extends AbsDBWorker{

	public String getTablename(String sharding){
		final String tablename = dbname+"."+I_constant.db_userdb_tablename+sharding;
		return tablename;
	}
	
    protected String SQLTemplate(){
        final String sql = "INSERT INTO ?(uid,userid,pathid,lat,lng,info,altitude,daynum,date,time)"
        		+ " VALUES (?,?,?,?,?,?,?,?,?,?)"; 
        System.out.println(sql);
    	return sql;
    }
}
