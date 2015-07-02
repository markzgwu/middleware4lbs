package org.assistants.dbsp.baidumaps;

import java.io.*;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.assistants.dbsp.jsonprocessor.Jsonsmart_OneLine;
import org.parameters.I_constant;
import org.tools.forFile.FileTool;

public final class ShrinkJson {
	String myfile;
	String backup_file;
	
	public ShrinkJson(String jsonfile){
		init(jsonfile);
	}
	
	public void init(String jsonfile){
		myfile = jsonfile;
		backup_file = FileTool.GenFileName(myfile, "backup");
	}
	
	void execute() throws Exception{
		FileTool.RenameFile(myfile, backup_file);
		final BufferedReader br = FileTool.getBufferedReader(backup_file);
		final BufferedWriter bw = FileTool.getBufferedWriter(myfile);
		String tempString = null;
		HashSet<String> buf = new HashSet<String>();
        while ((tempString = br.readLine()) != null) {
        	String uid = (new Jsonsmart_OneLine(tempString)).UIDFromOneLine();
        	boolean b = buf.add(uid);
        	if(b){
                bw.append(tempString+"\n");
                bw.flush();
        	}
        }
        br.close();
        System.out.println("BUF SIZE:"+buf.size());
	}
	
	public static void main(String[] args) {
		System.out.println(args[0]);
		
		String cmd = "";
		for(String s:args){
			cmd+=s+" ";
		}
		
		System.out.println(cmd);
		String filepath = I_constant.default_POIDB_filepath;
		String file = args[0];
		
		if(args.length>2){
			System.out.println("shrinkjson d:\\POIdatabase.json");
			return;
		}
		
		if(StringUtils.isBlank(file)){
			return;
		}else{
			filepath = file;
		}
		
		try {
			new ShrinkJson(filepath).execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
