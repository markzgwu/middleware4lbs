package org.zgwu4lab.lbs.index.grid;

import java.io.*;
import java.util.*;

import org.assistants.dbsp.jsonprocessor.Jsonsmart_OneLine;
import org.parameters.I_constant;
import org.tools.forFile.FileTool;
import org.zgwu4lab.lbs.datamodel.json.JsonIndexEntry;
import org.zgwu4lab.lbs.datamodel.json.JsonPOI;

import com.alibaba.fastjson.JSON;

public final class GridIndexer {
	final String data_file = I_constant.default_POIDB_filepath; 
	final String index_file = FileTool.GenIndexNameFromDB(data_file);
	final StringBuffer strBuf = new StringBuffer();
	final HashMap<String,String> index = new HashMap<String,String>();
	void bulidIndex() throws Exception{
		final BufferedReader br = FileTool.getBufferedReader(data_file);
		
		String strLine = null;

        while ((strLine = br.readLine()) != null) {
        	JsonPOI poi = (new Jsonsmart_OneLine(strLine)).POIFromOneLIne();
        	putPOI(poi);
        }
        br.close();
	}
	
	void putPOI(JsonPOI poi){
		String gridid = GridEncoder.leftbuttom(poi.getLocation());
		System.out.println(gridid+":"+poi.getLocation().getLat()+","+poi.getLocation().getLng());
    	String uid = poi.getUid();
    	boolean b = index.containsKey(gridid);
    	if(b){
    		String girdidarray = index.get(gridid);
    		girdidarray+=uid+",";
    		index.put(gridid, girdidarray);
    	}else{
    		index.put(gridid, uid+",");
    	}
	}
	
	void createIndex() throws Exception{
		
		for(String key : index.keySet()){
			String line = index.get(key);
			line = line.substring(0, line.length()-1);
			JsonIndexEntry e = new JsonIndexEntry();
			e.setGridUid(key);
			e.setPOIUidArray(line);
			String jsonline = JSON.toJSONString(e);
			strBuf.append(jsonline+"\n");
		}

	}
	
	void saveIndex() throws Exception{
		FileTool.DeleteFile(index_file);
		final BufferedWriter bw = FileTool.getBufferedWriter(index_file);
        bw.append(strBuf.toString());
        bw.flush();	
        bw.close();
	}
	
	void execute() throws Exception{
		bulidIndex();
		createIndex();
		saveIndex();
	}
	
	public static void main(String[] args) {
		
		try {
			new GridIndexer().execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
