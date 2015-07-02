package org.projects.preprocessing;

import java.io.BufferedReader;

import org.apache.commons.lang.StringUtils;
import org.tools.forFile.FileTool;

import com.alibaba.fastjson.JSON;

public final class MovingObjectsReader {

	public void read(){
		String filepath = "D:\\_workshop\\1336.txt";
		try {
			BufferedReader br = FileTool.getBufferedReader(filepath);
			String oneline = br.readLine();
			//int count = 0;
			System.out.println(oneline);//Read the first line;
			while(StringUtils.isNotBlank( oneline = br.readLine()) ){
				MovingObject mo = new MovingObject(oneline);
				
				if(mo.getTimestamp().equals("1")){
					break;
				}
				
				System.out.println(JSON.toJSONString(mo));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
