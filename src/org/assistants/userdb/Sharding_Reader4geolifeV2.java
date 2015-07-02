package org.assistants.userdb;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

import org.parameters.I_constant;
import org.tools.*;
import org.tools.forFile.FileTool;
import org.tools.forFile.ListFileInFolder;

import com.alibaba.fastjson.JSON;

public final class Sharding_Reader4geolifeV2{
	static int startuserid = 0;
	static String currentfile = null;
	static boolean b = false;
	static Sharding_UserDBWorkerV2 worker = new Sharding_UserDBWorkerV2();
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
		if(!b){
			worker.Batch();
		}

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
			worker.getTablename(pathid.substring(0,4));
			int usernum = Integer.parseInt(userid);
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
		block = null;
		path = null;
		return r;
	}	
	
	static void Readfile() throws Exception{
		worker.init_ps();
		System.out.println("init_ps;");

		String filepath = currentfile;
		System.out.println(filepath);
		BufferedReader bf = FileTool.getBufferedReader(filepath);
		String line = null;
		while((line=bf.readLine())!=null){
			if(checkline(line)){
				String[] dbfields = processline(line);
				//StringTool.Show(dbfields);
				worker.add_db_dup_key_batch(dbfields);
				dbfields = null;
			}
			line = null;
		}
		bf.close();
		bf = null;
		filepath = null;
		worker.Batch();
		System.out.print("batch;");
		worker.close_ps();
		System.out.println("close_ps;");
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
		dbfields = new String[11];
		for(int i=0;i<7;i++){
			dbfields[4+i] = fields[i];
		}
		fields = null;
		
		String[] ids =  ParsePath();
		for(int i=0;i<2;i++){
			dbfields[2+i] = ids[i];
		}
		ids=null;
		
		//uid,userid,pathid,lat,lng,info,altitude,daynum,date,time;
		UserEntry e = new UserEntry();
		e.userid = dbfields[2];
		e.pathid = dbfields[3];
		e.lat = dbfields[4];
		e.lng = dbfields[5];
		e.info = dbfields[6];
		e.altitude = dbfields[7];
		e.daynum = dbfields[8];
		e.date = dbfields[9];
		e.time = dbfields[10];

		String e_json = JSON.toJSONString(e);
		
		dbfields[1] = PKTool.PK(e_json);
		//dbfields[0] = worker.getTablename(e.pathid.substring(0,4));
		dbfields[0] = "USERDB";
		
		e_json = null;
		e = null;
		return dbfields;
	}
	
	public static void main(String[] args){
			if(args.length<1){
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
