package org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone;

import org.constants.Constants;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

public class BufferWorker {

	static BufferManager task = null;
	static  CacheManager manager = null;
	
	static CacheManager getCacheManager(){
		//System.out.println(File.separator);
		//String sep = "/";
		String path = Constants.Classpath;
		path += "ehcache.xml";
		System.out.println(path);
		if(manager == null){
			manager = new CacheManager(path);
		}
        
        return manager;
	}	
	
	public static final BufferManager getBuffer(){
		if(task==null){
			task = getBufferTask();
		}
		return task;
	}
	
	static BufferManager getBufferTask(){

        Ehcache cache = getCacheManager().getEhcache("sampleCache1");
        BufferManager task = new BufferManager(cache);
        return task;
	}
	
	public static void main(String[] args) {
		//System.out.println(File.separator);
		
		for(int i=0;i<20;i++){
			CellData tmpcelldata = new CellData();
			tmpcelldata.setCellID("abc"+i); 
			task.write(tmpcelldata.getCellID(), tmpcelldata);
		}
		
		for(int i=0;i<20;i++){
			System.out.println(task.read("abc"+i).getCellID());
		}
		
		
	}

}
