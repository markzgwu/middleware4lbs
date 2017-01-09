package org.assistants.userdb;

import java.io.BufferedReader;
import java.io.File;

import org.parameters.I_constant;
import org.tools.PKTool;
import org.tools.forFile.FileTool;
import org.tools.forFile.ListFileInFolder;

import com.alibaba.fastjson.JSON;

public final class Reader4geolife{
	static String currentfile = null;
	
	static UserDBWorker worker = new UserDBWorker();
	static void run() throws Exception{
		worker.init();
		
		ListFileInFolder task = new ListFileInFolder();
		task.refreshFileList(I_constant.default_userreq_folder);
		System.out.println("Sending Data to DB!");
		for(String file:task.getFilelist()){
			//System.out.println(file);
			currentfile = file;
			operator();
		}
		
		worker.close();
	}
		
	static boolean checkpath(String file){
		boolean r = file.contains(".plt");
		return r;
	}
	
	static String[] currentIDs = null;
	static void operator() throws Exception{
		String filepath = currentfile;
		if(checkpath(filepath)){
			currentIDs = ParsePath();
			System.out.println(filepath);
			if(currentIDs!=null){
				String userid = currentIDs[0];
				String pathid = currentIDs[1];
				System.out.println(userid+";"+pathid);
				opetatorfile();
			}

		}
	}
	
	static String[] ParsePath(){
		String path  = currentfile;
		String[] r = null;
		String[] block = path.split("\\"+File.separator);
		//System.out.println(block.length+";"+File.separator);
		if(block.length==7){
			r = new String[2];
			r[0] = block[4];
			r[1] = block[6];
		}
		return r;
	}	
	
	static void opetatorfile() throws Exception{
		String filepath = currentfile;
		BufferedReader bf = FileTool.getBufferedReader(filepath);
		String line = null;
		while((line=bf.readLine())!=null){
			if(checkline(line)){
				String[] dbfields = openatorline(line);
				//show(dbfields);
				worker.add_db_dup_key_batch(dbfields);
			}else{
				//System.out.println("ignored content:"+line);
			}
		}
		bf.close();
		worker.Batch();
	}
	
	static boolean checkline(String line){
		boolean b = false;
		String[] fields = line.split(",");
		if ((fields!=null)&&(fields.length==7)){
			b = true;
		}
		return b;
	}
	
	static String[] openatorline(String line){
		//System.out.println(line);
		String[] dbfields = null;
		String[] fields = line.split(",");
		/*
		if(fields.length!=7){
			return dbfields;
		}
		*/
		
		dbfields = new String[10];
		for(int i=0;i<7;i++){
			dbfields[3+i] = fields[i];
		}
		
		String[] ids =  ParsePath();
		for(int i=0;i<2;i++){
			dbfields[1+i] = ids[i];
		}
		
		//uid,userid,pathid,lat,lng,info,altitude,daynum,date,time;
		UserEntry e = new UserEntry();
		e.userid = dbfields[1];
		e.pathid = dbfields[2];
		e.lat = dbfields[3];
		e.lng = dbfields[4];
		e.info = dbfields[5];
		e.altitude = dbfields[6];
		e.daynum = dbfields[7];
		e.date = dbfields[8];
		e.time = dbfields[9];
		String e_json = JSON.toJSONString(e);
		//System.out.println(e_json);
		String uid = PKTool.PK(e_json);
		dbfields[0] = uid; 
		/*
		String lat,lng,info,altitude,daynum,date,time;
		if(fields.length==7){
			lat = fields[0];
			lng = fields[1];
			info = fields[2];
			altitude = fields[3];
			daynum = fields[4];
			date = fields[5];
			time = fields[6];
		}
		*/
		
		return dbfields;
	}
	
	public static void main(String[] args){
			
			try {
				run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
