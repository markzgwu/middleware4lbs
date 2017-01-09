package org.zgwu4lab.lbs.framework.database.memory;

import java.util.HashMap;

import org.zgwu4lab.lbs.datamodel.geodata.node.POIentry;
import org.zgwu4lab.lbs.datamodel.json.JsonIndexEntry;

public enum SingletonPOILoader {  
	INSTANCE;
	
	final private HashMap<String,POIentry> LocalStorage_POIs = LoadingDBfromfile();
	private HashMap<String,POIentry> LoadingDBfromfile() {
		HashMap<String,POIentry> POIs = new HashMap<String,POIentry>();
		POIs = File_for_memory.ParsePOIs_ReadJsonFileByLines();
		return POIs;
	}
	
	final private HashMap<String,JsonIndexEntry> LocalStorage_POIs_Index = LoadingIndexfromfile();

	private HashMap<String,JsonIndexEntry> LoadingIndexfromfile() {
		HashMap<String,JsonIndexEntry> POIsIndex = new HashMap<String,JsonIndexEntry>();
		POIsIndex = File_for_memory.ParsePOIsIndex_ReadJsonFileByLines();
		return POIsIndex;
	}

	public final HashMap<String, POIentry> getLocalStorage_POIs() {
		return LocalStorage_POIs;
	}
	public final HashMap<String, JsonIndexEntry> getLocalStorage_POIs_Index() {
		return LocalStorage_POIs_Index;
	}
	
}
