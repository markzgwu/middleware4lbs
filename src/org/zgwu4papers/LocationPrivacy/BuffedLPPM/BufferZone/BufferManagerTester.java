package org.zgwu4papers.LocationPrivacy.BuffedLPPM.BufferZone;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

public class BufferManagerTester {

	public static void main(String[] args) {
		//System.out.println(File.separator);
		String sep = "/";
		String path = Class.class.getClass().getResource(sep).getPath();
		path += "ehcache.xml";
		System.out.println(path);
		
        CacheManager manager = new CacheManager(path);
        Ehcache cache = manager.getEhcache("sampleCache1");
		MyDataAccessClass task = new MyDataAccessClass(cache);

		for(int i=0;i<100;i++){
			task.writeSomeData("abc"+i, "abcvalue"+i);
		}
		for(int i=0;i<100;i++){
			System.out.println(task.readSomeData("abc"+i));
		}
		
		
	}

}
