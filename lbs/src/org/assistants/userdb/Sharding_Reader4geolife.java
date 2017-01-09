package org.assistants.userdb;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

import org.parameters.I_constant;
import org.tools.*;
import org.tools.forFile.FileTool;
import org.tools.forFile.ListFileInFolder;

import com.alibaba.fastjson.JSON;

public final class Sharding_Reader4geolife{
	static int startuserid = 0;
	static String currentfile = null;
	
	static Sharding_UserDBWorker worker = new Sharding_UserDBWorker();
	static void run() throws Exception{
		worker.init();
		ArrayList<String> filelist = new ArrayList<String>();
		ListFileInFolder task = new ListFileInFolder();
		String foldername = I_constant.default_userreq_folder;
		System.out.println(foldername);
		task.refreshFileList(foldername);
		filelist = task.getFilelist();
		task = null;
		System.out.println("Sending "+filelist.size()+" data files to DB!");
		
		for(String file:filelist){
			//System.out.println(file);
			currentfile = file;
			processfile();

		}

		worker.close_ps();
		worker.close();
		worker = null;
		filelist = null;
	}
	//static int processfilenum = 0;
	
	static boolean checkpath(String file){
		String[] currentIDs = null;
		boolean r = file.contains(".plt");
		if(r){
			currentIDs = ParsePath();
			String userid = currentIDs[0];
			String pathid = currentIDs[1];
			//System.out.println(StringTool.Show(currentIDs));
			worker.sharding = pathid.substring(0,4);
			int usernum = Integer.parseInt(userid);
			//System.out.print(usernum+","+worker.sharding);
			//r = (usernum>=0) && (worker.sharding.equals("2008"));
			r = (usernum>=startuserid);
		}
		currentIDs = null;
		return r;
	}
	
	

	static void processfile() throws Exception{
		String filepath = currentfile;
		if(checkpath(filepath)){
			Readfile();
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
	
	static void Readfile() throws Exception{
		String filepath = currentfile;
		System.out.println(filepath);
		worker.init_ps();
		BufferedReader bf = FileTool.getBufferedReader(filepath);
		String line = null;
		int linenum = 0;
		while((line=bf.readLine())!=null){
			if(checkline(line)){
				String[] dbfields = processline(line);
				//show(dbfields);
				worker.add_db_dup_key_batch(dbfields);
				linenum++;
				if(linenum%20==0){
					worker.Batch();
				}
				
				dbfields = null;
			}
			line = null;
		}
		if(linenum%20!=0){
			worker.Batch();
		}
		bf.close();
		bf = null;
		filepath = null;
		//worker.Batch();
		System.out.print("batch;\n");
		//worker.close_ps();
		//System.out.print("close_ps;");
	}
	
	static boolean checkline(String line){
		boolean b = false;
		String[] fields = line.split(",");
		if ((fields!=null)&&(fields.length==7)){
			b = true;
		}
		fields = null;
		return b;
	}
	
	static String[] processline(String line){
		//System.out.println(line);
		String[] dbfields = null;
		String[] fields = line.split(",");
		dbfields = new String[10];
		for(int i=0;i<7;i++){
			dbfields[3+i] = fields[i];
		}
		fields = null;
		String[] ids =  ParsePath();
		for(int i=0;i<2;i++){
			dbfields[1+i] = ids[i];
		}
		ids=null;
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
		e_json = null;
		e = null;
		dbfields[0] = uid;		
		return dbfields;
	}
	
	public static void main(String[] args){
			if(args[0]==null){
				startuserid = 0;
			}else{
			    startuserid = Integer.parseInt(args[0]);
			}

		    System.out.println("startuserid="+startuserid);
			try {
				run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
