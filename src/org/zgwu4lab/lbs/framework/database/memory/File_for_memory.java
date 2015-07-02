package org.zgwu4lab.lbs.framework.database.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;

import org.assistants.dbsp.jsonprocessor.Fastjson;
import org.parameters.I_constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.forFile.FileTool;
import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.datamodel.json.JsonIndexEntry;

import com.alibaba.fastjson.JSON;

public class File_for_memory {
	private static final Logger logger = LoggerFactory.getLogger(File_for_memory.class);
	
	private static final String db_filepath = I_constant.default_POIDB_filepath;
	private static final String index_filepath = FileTool.GenIndexNameFromDB(I_constant.default_POIDB_filepath);
	
	public static void main(String[] args) {
		HashMap<String,POIentry> POIs = ParsePOIs_ReadJsonFileByLines();
		logger.info("POI size:"+POIs.size());
	}

	public static POIentry ReadJsonFileByLineNum(String filepath,int lineNum) throws Exception{
		final String jsonstring_for_onePOI = ReadJsonByLineNum(filepath,lineNum);
		final POIentry POIs = Fastjson.parsePOIfromJson(jsonstring_for_onePOI);
        return POIs;
	}
	
	public static String ReadJsonByLineNum(String filepath,int lineNum) throws Exception{
		final File file = new File(filepath);
        final LineNumberReader reader = new LineNumberReader(new FileReader(file));
        	reader.setLineNumber(lineNum);
            String tempString = reader.readLine();
            reader.close();
         return tempString;
	}	
	
	public static HashMap<String,JsonIndexEntry> ParsePOIsIndex_ReadJsonFileByLines(){
		HashMap<String,JsonIndexEntry> POIsIndex = null;
		try {
			POIsIndex = ParsePOIsIndex_ReadJsonFileByLines(index_filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return POIsIndex;
	}
	
	public static HashMap<String,JsonIndexEntry> ParsePOIsIndex_ReadJsonFileByLines(String filepath) throws Exception{
		final HashMap<String,JsonIndexEntry> POIsIndex = new HashMap<String,JsonIndexEntry>();
		final File file = new File(filepath);
        final BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                JsonIndexEntry oneindex = JSON.parseObject(tempString, JsonIndexEntry.class);
                POIsIndex.put(oneindex.getGridUid(), oneindex);
                logger.debug("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
            logger.debug("Successfully loading the POI store file:"+filepath);
         return POIsIndex;
	}	
	
	public static HashMap<String,POIentry> ParsePOIs_ReadJsonFileByLines(){
		HashMap<String,POIentry> POIs = null;
		try {
			POIs = ParsePOIs_ReadJsonFileByLines(db_filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return POIs;
	}
	
	public static HashMap<String,POIentry> ParsePOIs_ReadJsonFileByLines(String filepath) throws Exception{
		final HashMap<String,POIentry> POIs = new HashMap<String,POIentry>();
		final File file = new File(filepath);
        final BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                POIentry onepoi = Fastjson.parsePOIfromJson(tempString);
                POIs.put(onepoi.getUid(), onepoi);
                logger.debug("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
            logger.debug("Successfully loading the POI store file:"+filepath);
         return POIs;
	}	
	
}
