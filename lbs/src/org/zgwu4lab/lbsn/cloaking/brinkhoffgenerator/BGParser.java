package org.zgwu4lab.lbsn.cloaking.brinkhoffgenerator;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.tools.forFile.FileTool;

import com.alibaba.fastjson.JSONArray;

public class BGParser {
	String filepath = "D:\\_workshop\\1338.txt";
	final public ArrayList<BGEntry> Data = new ArrayList<BGEntry>();
	
	public void init(){
		try {
			parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void parse() throws Exception {
		//String filepath = "D:\\_workshop\\1336.txt";
		BufferedReader br = FileTool.getBufferedReader(filepath);
		String line = null;
		while((line=br.readLine())!=null){
			filterline(line);
		}
	}
	
	void filterline(String line){
		if(!line.contains("Object_Id")){
			//System.out.println(line);
			//String output = JSON.toJSONString(parseline(line));
			//System.out.println(output);
			Data.add(parseline(line));
		}
	}
	
	BGEntry parseline(String line){
		String[] array = line.split(" ");
		//System.out.println(array.length);
		BGEntry e = new BGEntry();
		e.setUserid(array[0]);
		e.setTime(array[1]);
		e.setType(array[2]);
		e.setLatitude(array[3]);
		e.setLongitude(array[4]);
		return e;
	}
	
	public String printMassage(ArrayList<BGEntry> l){
		return JSONArray.toJSONString(l);
	}
	
	public ArrayList<BGEntry> filterbyUserid(String userid){
		final ArrayList<BGEntry> output = new ArrayList<BGEntry>();
		for(BGEntry e:Data){
			boolean b = userid.equals(e.userid);
			//System.out.println(userid+";"+e.userid+";"+b+";"+JSON.toJSONString(e));
			if(b){
				output.add(e);
				//System.out.println(userid+";"+e.userid+";"+b+";"+JSON.toJSONString(e));
			}
		}
		return output;
	}
	
	public ArrayList<BGEntry> filterbyTime(String time){
		final ArrayList<BGEntry> output = new ArrayList<BGEntry>();
		for(BGEntry e:Data){
			boolean b = time.equals(e.time);
			//System.out.println(userid+";"+e.userid+";"+b+";"+JSON.toJSONString(e));
			if(b){
				output.add(e);
				//System.out.println(time+";"+e.time+";"+b+";"+JSON.toJSONString(e));
			}
		}
		return output;
	}
	
	public BGEntry filterbyUseridAndTime(String userid,String time){
		BGEntry ret = null;
		for(BGEntry e:Data){
			boolean b = userid.equals(e.userid)&&time.equals(e.time);
			//System.out.println(userid+";"+e.userid+";"+b+";"+JSON.toJSONString(e));
			if(b){
				ret = e;
			}
		}
		return ret;
	}
	
	public ArrayList<BGEntry> traceusers(ArrayList<String> users, String time){
		final ArrayList<BGEntry> output = new ArrayList<BGEntry>();
		for(String e:users){
			BGEntry one = filterbyUseridAndTime(e,time);
			output.add(one);
		}		
		return output;
	}
	
	public ArrayList<String> users(ArrayList<BGEntry> anonymityset){
		final ArrayList<String> output = new ArrayList<String>();
		for(BGEntry e:anonymityset){
			output.add(e.userid);
		}		
		return output;
	}	
}
