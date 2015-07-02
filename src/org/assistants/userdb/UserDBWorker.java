package org.assistants.userdb;

import org.parameters.I_constant;
import org.zgwu4lab.lbs.framework.database.worker.AbsDBWorker;

public final class UserDBWorker extends AbsDBWorker{
	
    protected String SQLTemplate(){
        final String tablename = dbname+"."+I_constant.db_userdb_tablename;
        final String sql = "insert into "+tablename
        		+ " (uid,userid,pathid,lat,lng,info,altitude,daynum,date,time)"
        		+ " values (?,?,?,?,?,?,?,?,?,?)"; 
    	return sql;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
