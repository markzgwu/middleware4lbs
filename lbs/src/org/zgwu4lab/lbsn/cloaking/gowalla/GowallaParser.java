package org.zgwu4lab.lbsn.cloaking.gowalla;

import java.io.BufferedReader;

import org.tools.forFile.FileTool;

import com.alibaba.fastjson.JSON;

public class GowallaParser {
	public static void parse() throws Exception {
		String filepath = "D:\\_workshop\\Gowalla_totalCheckins.txt";
		BufferedReader br = FileTool.getBufferedReader(filepath);
		String line = null;
		while((line=br.readLine())!=null){
			String output = JSON.toJSONString(parseline(line));
			System.out.println(output);
		}
	}
	
	static GowallaEntry parseline(String line){
		String[] array = line.split("\\t");
		//System.out.println(array.length);
		GowallaEntry e = new GowallaEntry();
		e.setUser(array[0]);
		e.setTime(array[1]);
		e.setLatitude(array[2]);
		e.setLongitude(array[3]);
		e.setLocationid(array[4]);
		return e;
	}
}
